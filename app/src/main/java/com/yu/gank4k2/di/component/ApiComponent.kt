package me.yu.drxx.di.component


import com.yu.gank4k2.di.moudle.ApiModule
import com.yu.gank4k2.di.moudle.AppModule
import dagger.Component
import me.yu.drxx.http.ApiService
import javax.inject.Singleton

/**
 * 提供api和imageLoader
 * @author yu
 * *         Create on 2016/12/11.
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class, AppModule::class))
interface ApiComponent {
    fun apiService(): ApiService
}
