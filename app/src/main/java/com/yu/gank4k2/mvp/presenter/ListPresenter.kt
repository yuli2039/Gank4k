package me.yu.drxx.mvp.presenter


import com.yu.gank4k2.base.BasePresenter
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import me.yu.drxx.mvp.repository.ListModel
import javax.inject.Inject

/**
 * Created by yu on 2016/10/25.
 */
class ListPresenter
@Inject constructor(view: ListContract.View, model: ListModel)
    : BasePresenter<ListContract.View, ListModel>(view, model), ListContract.Presenter{

    override fun refresh(type: String, pageNum: Int): List<GankEntity>? {
        return null
    }

    override fun loadMore(type: String): List<GankEntity>? {
        return null
    }
}