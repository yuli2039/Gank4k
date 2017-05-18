package com.yu.gank4k2.rx

import com.yu.gank4k2.base.BaseView
import com.yu.gank4k2.http.ApiException
import com.yu.gank4k2.http.ErrorHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 订阅时自动显示progressbar的订阅者，必须在主线程订阅
 * @author yu
 * *         Create on 16/7/26.
 */
abstract class ApiSubscriber<T>
@JvmOverloads constructor(private val mBaseView: BaseView?, private val showToast: Boolean = mBaseView != null)
    : Observer<T> {

    override fun onSubscribe(d: Disposable?) {
        mBaseView?.showLoading()
    }

    override fun onComplete() {
        mBaseView?.hideLoading()
    }

    /**
     * 只要链式调用中抛出了异常都会走这个回调
     */
    override final fun onError(e: Throwable) {
        e.printStackTrace()
        mBaseView?.hideLoading()
        val handleException = ErrorHandler.handleException(e, showToast)
        onError(handleException)
    }

    open fun onError(ex: ApiException) {
        // 处理某些特殊的错误
    }
}