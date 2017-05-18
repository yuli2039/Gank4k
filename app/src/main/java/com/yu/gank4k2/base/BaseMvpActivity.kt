package com.yu.gank4k2.base

import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject


/**
 * inject presenter
 * @author yu
 */
abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity(), BaseView {

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
