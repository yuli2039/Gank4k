package com.yu.gank4k2.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseEmptyFragment
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Created by yu on 2017/3/7.
 */
class CategoryFragment : BaseEmptyFragment() {

    val fragmentList: ArrayList<Fragment> = arrayListOf(
            AndroidFragment.newInstance(AndroidFragment.TYPE_VALUE),
            RandomFragment(),
            RandomFragment(),
            SettingFragment()
    )

    override val layoutId: Int
        get() = R.layout.fragment_category

    override fun afterInitView() {
        val stringArray = activity.resources.getStringArray(R.array.category_tab_name)
        vpList.adapter = ListAdapter(fragmentManager, fragmentList, stringArray)
        stlListTab.setViewPager(vpList)
    }

    private class ListAdapter(fm: FragmentManager, val fmts: List<Fragment>, val titles: Array<String>)
        : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = fmts.get(position)

        override fun getCount(): Int = fmts.size

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}
