package com.mikeyyds.ui.item.core

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MikeAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mContext: Context? = null
    private var mInflater: LayoutInflater? = null
    private var dataSets = ArrayList<MikeDataItem<*, RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<MikeDataItem<*, RecyclerView.ViewHolder>>()


    init {
        this.mContext = context
        this.mInflater = LayoutInflater.from(context)
    }

    fun addItem(index: Int, item: MikeDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }

        val notifyPos: Int = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    fun addItems(items: List<MikeDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {
        val start: Int = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItem(index: Int): MikeDataItem<*, RecyclerView.ViewHolder>? {
        if (index > 0 && index < dataSets.size) {
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return remove
        } else {
            return null
        }
    }

    fun removeItem(item: MikeDataItem<*, out RecyclerView.ViewHolder>) {
        if (item != null) {
            val index = dataSets.indexOf(item)
            removeItem(index)
        }
    }


    override fun getItemViewType(position: Int): Int {

        val dataItem = dataSets.get(position)
        val type = dataItem.javaClass.hashCode()
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val dataItem = typeArrays.get(viewType)
        var view = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem: " + dataItem.javaClass.name + " must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mikeDataItem = dataSets.get(position)

        mikeDataItem.onBindData(holder, position)

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val mikeDataItem = dataSets.get(position)
                        val spanSize = mikeDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }
            }
        }
        super.onAttachedToRecyclerView(recyclerView)
    }

    private fun createViewHolderInternal(
        javaClass: Class<MikeDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        val superClass: Type? = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val arguments = superClass.actualTypeArguments
            for (argument in arguments) {

                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {
                    try {

                        return argument.getConstructor(View::class.java)
                            .newInstance(view) as RecyclerView.ViewHolder
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {}
    }

    fun refreshItem(mikeDataItem: MikeDataItem<*, out RecyclerView.ViewHolder>) {
        val index = dataSets.indexOf(mikeDataItem)
        notifyItemChanged(index)
    }

}