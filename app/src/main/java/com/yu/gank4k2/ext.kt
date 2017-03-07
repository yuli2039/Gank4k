package com.yu.gank4k2

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import java.io.Serializable

/**
 * 一些扩展方法
 * Created by yu on 2016/12/29.
 */
fun Activity.jump(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
}

fun Activity.jump(clazz: Class<out Activity>, key: String, serializable: Serializable) {
    startActivity(Intent(this, clazz).putExtra(key, serializable))
}

fun Fragment.jump(clazz: Class<out Activity>) {
    startActivity(Intent(this.activity, clazz))
}

fun Fragment.jump(clazz: Class<out Activity>, key: String, serializable: Serializable) {
    startActivity(Intent(this.activity, clazz).putExtra(key, serializable))
}