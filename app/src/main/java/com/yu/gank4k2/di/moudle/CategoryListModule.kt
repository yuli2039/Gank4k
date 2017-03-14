package com.yu.gank4k2.di.moudle

import dagger.Module
import dagger.Provides
import me.yu.drxx.mvp.ListContract

/**
 * 分类的列表，提供view依赖
 */
@Module
class CategoryListModule(private val view: ListContract.View) {

    @Provides fun provideView(): ListContract.View = view

}
