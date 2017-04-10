package com.yu.gank4k2.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * rx订阅管理

 * @author yu
 */
abstract class BasePresenter<V : BaseView> {

    protected var mView: V? = null
    private val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    fun attachView(view: V) {
        this.mView = view
    }

    /**
     * should be called in onDestory
     */
    fun detachView() {
        this.mView = null
        onUnsubscribe()
    }

    /**
     * Unsubscribe
     */
    private fun onUnsubscribe() {
        if (mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe()
    }

    /**
     * addSubscription
     */
    protected fun addSubscription(subscribe: Subscription) {
        mCompositeSubscription.add(subscribe)
    }

}
