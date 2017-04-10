package me.yu.drxx.mvp.repository

import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.http.ApiService
import me.yu.drxx.mvp.CategoryContract
import rx.Observable
import javax.inject.Inject

/**
 * Created by yu on 2016/12/27.
 */
class CategoryModel
@Inject constructor(private val apiService: ApiService) : CategoryContract.Model {

    override fun loadData(type: String, pageSize: Int, pageNum: Int): Observable<HttpResult<List<GankEntity>>> {
        return apiService.loadCategoryData(type, pageSize.toString(), pageNum.toString())
    }
}