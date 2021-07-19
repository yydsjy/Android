package com.mikeyyds.library.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mikeyyds.library.app.R

class SecondFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View=inflater.inflate(R.layout.fragment_home,container,false)
        val textView:TextView = root.findViewById(R.id.text_home)
        textView.text = "secondFragment"+System.currentTimeMillis().toString()
        return root
    }
}