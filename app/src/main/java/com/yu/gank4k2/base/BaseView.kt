package com.yu.gank4k2.base

import com.trello.rxlifecycle2.LifecycleTransformer

interface BaseView {
    fun showLoading()

    fun hideLoading()

    /**
     * rxlifecyle绑定生命周期
     */
    fun <T> bindLifecycle(): LifecycleTransformer<T>
}
