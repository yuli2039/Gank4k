package com.yu.gank4k2.di.moudle

import android.app.Activity

import com.tbruyelle.rxpermissions.RxPermissions
import com.yu.gank4k2.di.ActivityScope

import dagger.Module
import dagger.Provides

@Module
class PermissionsModule(private val mActivity: Activity) {

    @ActivityScope
    @Provides
    internal fun provideRxpermissions(): RxPermissions = RxPermissions(mActivity)

}
