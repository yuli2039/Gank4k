package me.yu.drxx.mvp.presenter


import com.yu.gank4k2.base.BasePresenter
import com.yu.gank4k2.rx.ApiSubscriber
import com.yu.gank4k2.rx.DefaultTransformer
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.ListContract
import me.yu.drxx.mvp.repository.ListModel
import javax.inject.Inject

/**
 * Created by yu on 2016/10/25.
 */
class ListPresenter
@Inject constructor() : BasePresenter<ListContract.View>(), ListContract.Presenter {

    @Inject lateinit var mModel: ListModel

    var pageIndex = 1

    override fun refresh(type: String, pageSize: Int) {
        pageIndex = 1
        addSubscription(
                mModel.loadData(type, pageSize, pageIndex)
                        .compose(DefaultTransformer<List<GankEntity>>())
                        .filter { mView != null }
                        .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                            override fun onNext(t: List<GankEntity>) {
                                mView?.onRefresh(t)
                            }
                        })
        )
    }

    override fun loadMore(type: String, pageSize: Int) {
        pageIndex++
        addSubscription(
                mModel.loadData(type, pageSize, pageIndex)
                        .compose(DefaultTransformer<List<GankEntity>>())
                        .filter { mView != null }
                        .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                            override fun onNext(t: List<GankEntity>) {
                                if (t.isEmpty()) {
                                    mView?.onNoMore()
                                } else {
                                    mView?.onLoadMore(t)
                                }
                            }
                        })
        )
    }

}