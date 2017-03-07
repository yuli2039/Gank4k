package com.yu.gank4k2.util

import android.os.Handler
import android.os.Looper

/**
 * @author yu
 * *         Create on 16/10/15.
 */
object HandlerUtils {

    private val sHandler = Handler(Looper.getMainLooper())

    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    fun runOnUI(r: Runnable) {
        sHandler.post(r)
    }

    fun runOnUIDelayed(r: Runnable, delayMills: Long) {
        sHandler.postDelayed(r, delayMills)
    }

    fun removeRunnable(r: Runnable?) {
        if (r == null) {
            sHandler.removeCallbacksAndMessages(null)
        } else {
            sHandler.removeCallbacks(r)
        }
    }
}
