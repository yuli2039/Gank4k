package com.yu.gank4k2.util

import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import com.yu.gank4k2.App

/**
 * Created by yu on 2017/5/18.
 */

object Toasty {

    @JvmOverloads fun show(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
        if (TextUtils.isEmpty(message))
            return
        val toast = Toast.makeText(App.context, message, duration)
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toast.show()
        } else {
            HandlerUtils.runOnUI(Runnable { toast.show() })
        }
    }
}
