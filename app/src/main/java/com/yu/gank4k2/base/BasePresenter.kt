package com.yu.gank4k2.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * rx订阅管理

 * @author yu
 */
abstract class BasePresenter<V : BaseView, M : BaseModel>(protected var mView: V?, protected var mModel: M) {

    private var mCompositeSubscription: CompositeSubscription? = null

    /**
     * should be called in onDestory
     */
    fun destory() {
        this.mView = null
        onUnsubscribe()
    }

    /**
     * Unsubscribe
     */
    private fun onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions())
            mCompositeSubscription!!.unsubscribe()
    }

    /**
     * addSubscription
     */
    protected fun addSubscription(subscribe: Subscription) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = CompositeSubscription()
        mCompositeSubscription!!.add(subscribe)
    }

}
