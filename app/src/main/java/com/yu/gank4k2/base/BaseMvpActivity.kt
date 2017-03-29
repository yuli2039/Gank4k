package com.yu.gank4k2.base

import android.widget.Toast
import javax.inject.Inject


/**
 * inject presenter
 * @author yu
 */
abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity(), BaseView {

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
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}
