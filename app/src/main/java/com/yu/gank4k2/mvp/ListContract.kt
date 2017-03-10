package me.yu.drxx.mvp

import com.yu.gank4k2.base.BaseModel
import com.yu.gank4k2.base.BaseView
import me.yu.drxx.entity.GankEntity

/**
 * Created by yu on 2016/10/25.
 */
interface ListContract {
    interface View : BaseView {
        fun onRefresh(data: List<GankEntity>?)
        fun onLoadMore(data: List<GankEntity>?)
    }

    interface Presenter {
        fun refresh(type: String, pageNum: Int = 1): List<GankEntity>?
        fun loadMore(type: String): List<GankEntity>?
    }

    interface Model : BaseModel {
        fun loadData(type: String, pageNum: Int = 1): List<GankEntity>?
    }
}