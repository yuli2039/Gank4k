package com.yu.gank4k2.rx

import com.google.gson.JsonParseException
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.base.BaseView
import org.json.JSONException
import retrofit2.HttpException

import rx.Subscriber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

/**
 * 订阅时自动显示progressbar的订阅者，必须在主线程订阅
 * @author yu
 * *         Create on 16/7/26.
 */
abstract class ApiSubscriber<T>
@JvmOverloads constructor(private val mBaseView: BaseView?, private val showLoading: Boolean = true)
    : Subscriber<T>() {

    override fun onStart() {
        if (showLoading)
            mBaseView?.showLoading()
    }

    override fun onCompleted() {
        if (showLoading)
            mBaseView?.hideLoading()
    }

    /**
     * 只要链式调用中抛出了异常都会走这个回调
     * 业务失败还是走onNext,此处的错误不关乎业务逻辑，直接弹出toast
     */
    override final fun onError(e: Throwable) {
        if (showLoading)
            mBaseView?.hideLoading()

        val failMsg = getFailMsg(e)
        if (null != failMsg)
            mBaseView?.toast(failMsg)

        e.printStackTrace()
    }

    fun getFailMsg(e: Throwable): String? {
        return when (e) {
            is ApiException -> e.message
            is ConnectException, is SocketTimeoutException, is HttpException -> App.context?.getString(R.string.network_timeout)
            is JsonParseException, is JSONException, is ParseException -> App.context?.getString(R.string.parse_error)
            else -> App.context?.getString(R.string.server_is_busy)
        }
    }

}