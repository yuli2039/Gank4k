package com.yu.gank4k2.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Created by yu on 2017/3/7.
 */
class TabEntity(
        private var selectedIcon: Int,
        private var unSelectedIcon: Int,
        private var tabTitle: String? = ""
)
    : CustomTabEntity {

    override fun getTabTitle(): String? {
        return tabTitle
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}
