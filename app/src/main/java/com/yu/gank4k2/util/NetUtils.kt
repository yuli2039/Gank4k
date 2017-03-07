package com.yu.gank4k2.util

import android.content.Context
import android.net.ConnectivityManager
import com.yu.gank4k2.App


/**
 * 网络工具类
 */
object NetUtils {

    val isOnline: Boolean
        get() {
            val networkInfo = (App.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable
        }

}
