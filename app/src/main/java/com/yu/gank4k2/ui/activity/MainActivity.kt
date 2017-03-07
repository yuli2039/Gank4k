package com.yu.gank4k2.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yu.gank4k2.R
import com.yu.gank4k2.entity.TabEntity
import com.yu.gank4k2.ui.fragment.Fragment1
import com.yu.gank4k2.ui.fragment.Fragment2
import com.yu.gank4k2.ui.fragment.Fragment3
import kotlinx.android.synthetic.main.activity_main.*
import me.yu.drxx.ui.BaseEmptyActivity

class MainActivity : BaseEmptyActivity() {

    val tabEntities: ArrayList<CustomTabEntity> by lazy {
        arrayListOf<CustomTabEntity>(
                TabEntity(R.mipmap.ic_launcher, R.mipmap.ic_launcher),
                TabEntity(R.mipmap.ic_launcher, R.mipmap.ic_launcher),
                TabEntity(R.mipmap.ic_launcher, R.mipmap.ic_launcher))
    }

    val fs: List<Fragment> by lazy {
        arrayListOf(Fragment1(), Fragment2(), Fragment3())
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun afterInitView() {

        topTabLayout.setTabData(tabEntities)
        topTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                rootViewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {}
        })

        rootViewPager.adapter = RootPagerAdapter(supportFragmentManager, fs)
        rootViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                topTabLayout.currentTab = position
            }
        })
    }

    private class RootPagerAdapter(fm: FragmentManager, val fmts: List<Fragment>)
        : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = fmts.get(position)

        override fun getCount(): Int = fmts.size
    }
}
