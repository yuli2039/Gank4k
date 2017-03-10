package com.yu.gank4k2.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import javax.inject.Inject

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseFragment<P : BasePresenter<*, *>> : Fragment(), BaseView {
    @Inject lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injectComponent()
        return inflater?.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView()
    }

    protected abstract val layoutId: Int

    protected abstract fun injectComponent()

    protected abstract fun afterInitView()

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun toast(msg: String?) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}