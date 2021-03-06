package io.iostwin.iostdex.netwrok

import io.iostwin.iostdex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetConfig {

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)//允许失败重试
        .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
        .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
        .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
        .addInterceptor(HttpLoggingInterceptor().run {
            this.level = HttpLoggingInterceptor.Level.BODY
            this
        })
        .addInterceptor(IOSTInterceptor())
        .build()

    val webSocketClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)//允许失败重试
        .readTimeout(300, TimeUnit.SECONDS)//设置读取超时时间
        .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
        .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
        .addInterceptor(HttpLoggingInterceptor().run {
            this.level = HttpLoggingInterceptor.Level.BODY
            this
        })
        .addInterceptor(IOSTInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> getService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}