package com.mikeyyds.library.app.demo.refresh

import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikeyyds.library.app.R
import com.mikeyyds.ui.refresh.MikeLottieOverView
import com.mikeyyds.ui.refresh.MikeRefresh
import com.mikeyyds.ui.refresh.MikeRefreshLayout
import com.mikeyyds.ui.refresh.MikeTextOverView

class MikeRefreshDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mike_refresh_demo)

        val refreshLayout = findViewById<MikeRefreshLayout>(R.id.refresh_layout)
        val xOverview = MikeTextOverView(this)
        val lottieOverView = MikeLottieOverView(this)

        refreshLayout.setRefreshOverView(xOverview)
        refreshLayout.setDisableRefreshScroll(false)
        refreshLayout.setRefreshListener(object : MikeRefresh.MikeRefreshListener {
            override fun enableRefresh(): Boolean {
                return true
            }

            override fun onRefresh() {
                Handler().postDelayed({ refreshLayout.refreshFinished() }, 5000)
            }

        })

        initRecyclerView()
    }

    private var recyclerView: RecyclerView? = null
    var myDataset = arrayOf("MikeRefresh", "MikeRefresh", "MikeRefresh","MikeRefresh", "MikeRefresh", "MikeRefresh")

    class MyAdapter(private val mDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var textView: TextView
            init {
                textView = v.findViewById(R.id.tv_title)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = mDataset[position]
        }

        override fun getItemCount(): Int {
            return mDataset.size
        }

    }

    private fun initRecyclerView() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        val mAdapter = MyAdapter(myDataset)
        recyclerView!!.setAdapter(mAdapter)
    }
}