package com.yu.gank4k2.ui.activity

import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseActivity
import com.yu.gank4k2.ui.fragment.MainFragment

class MainActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun afterInitView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment())
                .commit()
    }
}
