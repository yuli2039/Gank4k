package com.yu.gank4k2.mvp.presenter

import com.yu.gank4k2.base.BasePresenter
import com.yu.gank4k2.mvp.model.RandomModel
import com.yu.gank4k2.rx.ApiSubscriber
import com.yu.gank4k2.rx.DefaultTransformer
import com.yu.gank4k2.util.CategoryType
import me.yu.drxx.entity.GankEntity
import me.yu.drxx.mvp.RandomContract
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func3
import javax.inject.Inject

/**
 * Created by yu on 2017/3/30.
 */
class RandomPresenter
@Inject constructor() : BasePresenter<RandomContract.View>(), RandomContract.Presenter {

    @Inject lateinit var mModel: RandomModel
    private val pageSize = 5

    override fun refresh() {

        val girlsObservable = mModel.loadData(CategoryType.GIRLS, pageSize)
                .compose(DefaultTransformer<List<GankEntity>>())
        val androidObservable = mModel.loadData(CategoryType.ANDROID, pageSize)
                .compose(DefaultTransformer<List<GankEntity>>())
        val iosObservable = mModel.loadData(CategoryType.IOS, pageSize)
                .compose(DefaultTransformer<List<GankEntity>>())

        addSubscription(
                Observable.zip(girlsObservable, androidObservable, iosObservable,
                        object : Func3<List<GankEntity>, List<GankEntity>, List<GankEntity>, Map<String, List<GankEntity>>> {
                            override fun call(t1: List<GankEntity>, t2: List<GankEntity>, t3: List<GankEntity>): Map<String, List<GankEntity>> {
                                return mapOf(CategoryType.GIRLS to t1, CategoryType.ANDROID to t2, CategoryType.IOS to t3)
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter { mView != null }
                        .subscribe(object : ApiSubscriber<Map<String, List<GankEntity>>>(mView) {
                            override fun onNext(t: Map<String, List<GankEntity>>) {
                                mView?.onRefresh(t)
                            }
                        })
        )
    }
}