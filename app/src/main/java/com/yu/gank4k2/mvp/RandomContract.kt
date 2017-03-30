package me.yu.drxx.mvp

import com.yu.gank4k2.base.BaseModel
import com.yu.gank4k2.base.BaseView
import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import rx.Observable

/**
 * Created by yu on 2016/10/25.
 */
interface RandomContract {
    interface View : BaseView {
        fun onRefresh(data: Map<String, List<GankEntity>>)
    }

    interface Presenter {
        fun refresh()
    }

    interface Model : BaseModel {
        fun loadData(type: String, pageSize: Int = 10): Observable<HttpResult<List<GankEntity>>>
    }
}