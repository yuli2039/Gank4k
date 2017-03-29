package com.yu.gank4k2.util

/**
 * Created by yu on 2017/3/29.
 */
enum class CategoryType {
    ANDROID {
        override fun getTypeString(): String = "Android"
    },
    IOS {
        override fun getTypeString(): String = "iOS"
    },
    GIRLS {
        override fun getTypeString(): String = "福利"
    };

    abstract fun getTypeString(): String

}
