package com.yu.gank4k2

import android.app.Application
import android.content.Context
import com.yu.gank4k2.di.moudle.ApiModule
import com.yu.gank4k2.di.moudle.AppModule
import com.yu.gank4k2.util.AppManager
import com.yu.gank4k2.util.Constant
import io.realm.Realm
import me.yu.drxx.di.component.ApiComponent
import me.yu.drxx.di.component.DaggerApiComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor




/**
 * @author yu
 * *         Create on 2017/1/9.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AppManager.getInstance().init(this)

        appComponent = DaggerApiComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(HttpUrl.parse(Constant.BASE_URL), interceptors))
                .build()

        Realm.init(this)
    }

    // 这里返回okhttp的拦截器的集合，这里只是一个示例，添加一个header
    val interceptors: List<Interceptor> = arrayListOf(
            Interceptor { chain ->
                val request = chain.request().newBuilder()
                        .addHeader("token", "qwe")
                        .build()
                chain.proceed(request)
            }
    )

    companion object {
        var context: Context? = null
            private set
        var appComponent: ApiComponent? = null
            private set
    }
}
