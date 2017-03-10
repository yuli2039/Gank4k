package com.yu.gank4k2.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.yu.gank4k2.R
import com.yu.gank4k2.ui.fragment.Fragment1
import com.yu.gank4k2.ui.fragment.Fragment3
import com.yu.gank4k2.ui.fragment.ListFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.yu.drxx.ui.BaseEmptyActivity

class MainActivity : BaseEmptyActivity() {

    val fragmentList: List<Fragment> by lazy {
        arrayListOf(Fragment1(), ListFragment(), Fragment3())
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun afterInitView() {

        vpContent.adapter = RootPagerAdapter(supportFragmentManager, fragmentList)
        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> rgMainTab.check(R.id.rbRandom)
                    1 -> rgMainTab.check(R.id.rbList)
                    2 -> rgMainTab.check(R.id.rbUser)
                }
            }
        })

        rgMainTab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbRandom -> vpContent.currentItem = 0
                R.id.rbList -> vpContent.currentItem = 1
                R.id.rbUser -> vpContent.currentItem = 2
            }
        }

        ibtnSearch.setOnClickListener {
            toast("asdf")
            // todo
        }
    }

    private class RootPagerAdapter(fm: FragmentManager, val fmts: List<Fragment>)
        : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = fmts.get(position)

        override fun getCount(): Int = fmts.size
    }
}
