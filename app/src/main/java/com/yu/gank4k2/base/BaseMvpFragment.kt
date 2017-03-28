package com.yu.gank4k2.base

import android.text.TextUtils
import android.widget.Toast
import javax.inject.Inject

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseMvpFragment<P : BasePresenter<*, *>> : BaseFragment(), BaseView {

    @Inject lateinit var mPresenter: P

    override fun onDestroy() {
        mPresenter.destory()
        super.onDestroy()
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}