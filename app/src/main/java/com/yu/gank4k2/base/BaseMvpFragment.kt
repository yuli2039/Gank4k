package com.yu.gank4k2.base

import android.widget.Toast
import javax.inject.Inject

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject lateinit var mPresenter: P

    override fun onDestroy() {
        mPresenter.destory()
        super.onDestroy()
    }

    override abstract fun injectComponent()

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun toast(msg: String) {
        if (msg.length > 10)
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}