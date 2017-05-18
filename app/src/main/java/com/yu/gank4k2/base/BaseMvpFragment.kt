package com.yu.gank4k2.base

import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject lateinit var mPresenter: P

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override abstract fun injectComponent()

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    /**
     * Observable生命周期绑定
     */
    override fun <T> bindLifecycle(): LifecycleTransformer<T> = bindToLifecycle()
}