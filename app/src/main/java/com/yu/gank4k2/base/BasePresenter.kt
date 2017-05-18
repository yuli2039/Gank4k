package com.yu.gank4k2.base


/**
 * rx订阅管理
 * @author yu
 */
abstract class BasePresenter<V : BaseView> {

    protected var mView: V? = null

    fun attachView(view: V) {
        this.mView = view
    }

    fun detachView() {
        this.mView = null
    }

}
