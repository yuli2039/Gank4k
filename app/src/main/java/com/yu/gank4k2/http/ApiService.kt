package me.yu.drxx.http

import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ApiService {

    @GET("api/data/{type}/{pageNum}/{page}")
    fun loadCategoryData(@Path("type") type: String,
                         @Path("pageNum") pageNum: String,
                         @Path("page") page: String)
            : Observable<HttpResult<List<GankEntity>>>
}