package com.yu.gank4k2.di.moudle

import android.app.Application
import android.content.Context

import com.yu.gank4k2.di.ApplicationContext

import dagger.Module
import dagger.Provides

/**
 * 提供全局context
 * Created by yu on 2016/12/22.
 */
@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context = mApplication.applicationContext

}
