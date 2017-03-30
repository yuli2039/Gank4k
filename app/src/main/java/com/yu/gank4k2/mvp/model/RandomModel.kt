package com.yu.gank4k2.mvp.model

import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.http.ApiService
import me.yu.drxx.mvp.RandomContract
import rx.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/3/30.
 */
class RandomModel
@Inject constructor(private val apiService: ApiService) : RandomContract.Model {

    override fun loadData(type: String, pageSize: Int): Observable<HttpResult<List<GankEntity>>> {
        return apiService.loadRandomData(type, pageSize.toString())
    }
}