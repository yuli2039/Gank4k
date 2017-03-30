package me.yu.drxx.http

import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ApiService {

    @GET("api/data/{type}/{pageSize}/{pageNum}")
    fun loadCategoryData(@Path("type") type: String,
                         @Path("pageSize") pageSize: String,
                         @Path("pageNum") pageNum: String)
            : Observable<HttpResult<List<GankEntity>>>

    @GET("api/random/data/{type}/{pageSize}")
    fun loadRandomData(@Path("type") type: String,
                       @Path("pageSize") pageSize: String)
            : Observable<HttpResult<List<GankEntity>>>
}