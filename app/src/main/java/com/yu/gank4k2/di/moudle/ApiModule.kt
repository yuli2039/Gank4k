package com.yu.gank4k2.di.moudle

import android.content.Context
import android.util.Log
import com.yu.gank4k2.di.ApplicationContext
import com.yu.gank4k2.util.CacheDirUtil
import com.yu.gank4k2.util.Constant
import dagger.Module
import dagger.Provides
import me.yu.drxx.http.ApiService
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 提供ApiService
 * Created by yu on 2016/12/22.
 */
@Module
class ApiModule(private val mApiUrl: HttpUrl, private val mInterceptors: List<Interceptor>?) {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mApiUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(@ApplicationContext context: Context, logger: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(Constant.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(Cache(CacheDirUtil.getCacheFile(context), Constant.HTTP_CACHE_SIZE))

        if (mInterceptors != null && !mInterceptors.isEmpty()) {
            for (interceptor in mInterceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        return builder.addInterceptor(logger).build()
    }

    @Singleton
    @Provides
    internal fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.e("yu", message) }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}
