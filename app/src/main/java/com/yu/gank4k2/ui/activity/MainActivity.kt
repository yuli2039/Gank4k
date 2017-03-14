package com.yu.gank4k2.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.yu.gank4k2.R
import com.yu.gank4k2.ui.fragment.RandomFragment
import com.yu.gank4k2.ui.fragment.SettingFragment
import com.yu.gank4k2.ui.fragment.CategoryFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.yu.drxx.ui.BaseEmptyActivity

class MainActivity : BaseEmptyActivity() {

    val fragmentList: List<Fragment> = arrayListOf(RandomFragment(), CategoryFragment(), SettingFragment())

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun afterInitView() {

        vpContent.adapter = RootPagerAdapter(supportFragmentManager, fragmentList)

        stlMainTab.setCustomTabView(object : SmartTabLayout.TabProvider {
            override fun createTabView(container: ViewGroup?, position: Int, adapter: PagerAdapter?): View {
                val tabItem = LayoutInflater.from(this@MainActivity).inflate(R.layout.layout_main_tab_item, null)
                tabItem.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
                val imageView = tabItem.findViewById(R.id.ivTab) as ImageView
                when (position) {
                    0 -> imageView.setImageResource(R.drawable.selector_main_tab_random)
                    1 -> imageView.setImageResource(R.drawable.selector_main_tab_list)
                    2 -> imageView.setImageResource(R.drawable.selector_main_tab_user)
                }
                return tabItem
            }
        })
        stlMainTab.setViewPager(vpContent)

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
