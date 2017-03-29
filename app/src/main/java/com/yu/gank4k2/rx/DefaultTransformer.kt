package com.yu.gank4k2.rx

import android.content.res.Resources
import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.http.HttpResult
import com.yu.gank4k2.util.NetUtils
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * 错误处理和线程切换
 * @author yu
 */
class DefaultTransformer<T> : Observable.Transformer<HttpResult<T>, T> {
    override fun call(tObservable: Observable<HttpResult<T>>): Observable<T> {
        return tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onBackpressureLatest()
                .doOnSubscribe {
                    if (!NetUtils.isOnline)
                        throw ApiException(App.context?.getString(R.string.network_disconnected))
                }
                .flatMap(Func1<HttpResult<T>, Observable<T>> { result ->
                    return@Func1 if (result.isError)
                        Observable.error<T>(ApiException(App.context?.getString(R.string.server_error)))
                    else
                        Observable.just(result.results)
                })
    }
}
