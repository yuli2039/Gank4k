package me.yu.drxx.mvp.repository

import com.yu.gank4k2.http.HttpResult
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.http.ApiService
import me.yu.drxx.mvp.CategoryListContract
import rx.Observable
import javax.inject.Inject

/**
 * Created by yu on 2016/12/27.
 */
class CategoryListModel
@Inject constructor(private val apiService: ApiService) : CategoryListContract.Model {

    override fun loadData(type: String, pageSize: Int, pageNum: Int): Observable<HttpResult<List<GankEntity>>> {
        return apiService.loadCategoryData(type, pageSize.toString(), pageNum.toString())
    }
}