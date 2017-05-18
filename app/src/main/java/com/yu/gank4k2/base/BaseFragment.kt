package com.yu.gank4k2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseFragment : com.trello.rxlifecycle2.components.support.RxFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injectComponent()
        return inflater?.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView()
    }

    protected abstract val layoutId: Int

    open fun injectComponent() {}

    protected abstract fun afterInitView()

}