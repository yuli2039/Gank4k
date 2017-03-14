package me.yu.drxx.mvp

import com.yu.gank4k2.base.BaseModel
import com.yu.gank4k2.base.BaseView
import com.yu.gank4k2.rx.HttpResult
import me.yu.drxx.entity.GankEntity
import rx.Observable

/**
 * Created by yu on 2016/10/25.
 */
interface ListContract {
    interface View : BaseView {
        fun onRefresh(data: List<GankEntity>?)
        fun onLoadMore(data: List<GankEntity>?)
        fun onNoMore()
    }

    interface Presenter {
        fun refresh(type: String, pageSize: Int = 10)
        fun loadMore(type: String, pageSize: Int = 10)
    }

    interface Model : BaseModel {
        fun loadData(type: String, pageSize: Int = 10, pageNum: Int = 1): Observable<HttpResult<List<GankEntity>>>
    }
}