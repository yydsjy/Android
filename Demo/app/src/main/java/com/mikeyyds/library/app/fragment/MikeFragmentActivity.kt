package com.mikeyyds.library.app.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.mikeyyds.library.app.R

class MikeFragmentActivity : AppCompatActivity() {
    private val FRAGMENT_TAG = "FRAGMENT_TAG"
    private var fragments:List<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

//        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
//        if (fragment==null){
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.container,firstFragment,FRAGMENT_TAG)
//                .commit()
//        }

        fragments = listOf(firstFragment,secondFragment)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = MyFragmentStateAdpater()
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    inner class MyFragmentStateAdpater:FragmentStateAdapter(this){
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments.get(position)
        }

    }
}