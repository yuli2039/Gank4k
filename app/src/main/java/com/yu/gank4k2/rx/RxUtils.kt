package com.yu.gank4k2.rx

import com.yu.gank4k2.App
import com.yu.gank4k2.R
import com.yu.gank4k2.http.HttpResult
import com.yu.gank4k2.http.NetworkDisconnectException
import com.yu.gank4k2.util.NetUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yu on 2017/5/18.
 */
object RxUtils {

    /**
     * 访问网络通用Transformer，判断了网络，切换线程，剥离结果
     */
    fun <T> apiTransformer(): ObservableTransformer<HttpResult<T>, T> {
        return ObservableTransformer {
            it.compose(checkNetwork<HttpResult<T>>())
                    .compose(ioToMain<HttpResult<T>>())
                    .compose(parseResult<T>())
        }
    }

    /**
     * io线程到主线程
     */
    fun <T> ioToMain(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 检查网络
     */
    fun <T> checkNetwork(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnSubscribe {
                if (!NetUtils.isOnline)
                    throw NetworkDisconnectException()
            }
        }
    }

    /**
     * 剥离接口返回的对象
     */
    fun <T> parseResult(): ObservableTransformer<HttpResult<T>, T> {
        return ObservableTransformer {
            it.flatMap {
                if (it.isError)
                    Observable.error<T>(NetworkDisconnectException())
                else
                    Observable.just(it.results!!)
            }
        }
    }
}
