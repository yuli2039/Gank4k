package com.yu.gank4k2.http

import android.net.ParseException
import android.util.SparseArray
import com.google.gson.JsonParseException
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.util.Toasty
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by yu on 2017/5/18.
 */
object ErrorHandler {

    private const val ERROR_CODE_TIME_OUT = 0x01
    private const val ERROR_CODE_HTTP_EX = 0x02
    private const val ERROR_CODE_PARSE_EX = 0x03
    private const val ERROR_CODE_BUSY_EX = 0x04
    private const val ERROR_CODE_NET_EX = 0x05

    private val ERR = SparseArray<String>(8)

    init {
        ERR.put(ERROR_CODE_TIME_OUT, App.context?.getString(R.string.network_timeout))
        ERR.put(ERROR_CODE_HTTP_EX, App.context?.getString(R.string.server_error))
        ERR.put(ERROR_CODE_PARSE_EX, App.context?.getString(R.string.parse_error))
        ERR.put(ERROR_CODE_BUSY_EX, App.context?.getString(R.string.server_is_busy))
        ERR.put(ERROR_CODE_NET_EX, App.context?.getString(R.string.network_disconnected))
    }

    fun handleException(e: Throwable, showToast: Boolean): ApiException {
        val exception = parseException(e)
        if (showToast) Toasty.show(exception.message)

        return exception
    }

    private fun parseException(e: Throwable): ApiException {
        if (e is ApiException) {
            return e
        } else if (e is NetworkDisconnectException) {// 网络链接断开
            return ApiException(ERROR_CODE_NET_EX, ERR[ERROR_CODE_NET_EX])
        } else if (e is ConnectException || e is SocketTimeoutException) {// 超时
            return ApiException(ERROR_CODE_TIME_OUT, ERR[ERROR_CODE_TIME_OUT])
        } else if (e is HttpException) {// server 异常
            return ApiException(ERROR_CODE_HTTP_EX, ERR[ERROR_CODE_HTTP_EX])
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            return ApiException(ERROR_CODE_PARSE_EX, ERR[ERROR_CODE_PARSE_EX])
        } else {
            return ApiException(ERROR_CODE_BUSY_EX, ERR[ERROR_CODE_BUSY_EX])
        }
    }
}

