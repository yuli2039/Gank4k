package com.yu.gank4k2.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseEmptyFragment
import kotlinx.android.synthetic.main.fragment_root_list.*

/**
 * Created by yu on 2017/3/7.
 */
class ListFragment : BaseEmptyFragment() {

    val fragmentList: List<Fragment> by lazy {
        arrayListOf(Fragment1(), Fragment3())
    }

    override val layoutId: Int
        get() = R.layout.fragment_root_list

    override fun afterInitView() {
        val stringArray = activity.resources.getStringArray(R.array.list_tab_name)
        vpList.adapter = ListAdapter(fragmentManager, fragmentList, stringArray)
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
