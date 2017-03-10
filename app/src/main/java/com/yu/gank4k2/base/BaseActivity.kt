package com.yu.gank4k2.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import javax.inject.Inject


/**
 * inject presenter
 * @author yu
 */
abstract class BaseActivity<P : BasePresenter<*, *>> : AppCompatActivity(), BaseView {

    @Inject lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        injectComponent()
        afterInitView()
    }

    override fun onDestroy() {
        mPresenter.destory()
        super.onDestroy()
    }

    protected abstract val layoutId: Int

    protected abstract fun afterInitView()

    protected abstract fun injectComponent()

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}
