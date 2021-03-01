package com.crypto.di

import com.crypto.BuildConfig
import com.crypto.data.server.CurrencyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val serverModule = module {
    single { getCurrencyApi() }
}

const val BASE_URL = "https://data.messari.io/"
const val HEADER_API_KEY = "x-messari-api-key"

fun getCurrencyApi(): CurrencyApi {
    val client = OkHttpClient.Builder()
        .addInterceptor(createLoggingInterceptor())
        .addInterceptor(ApiKeyInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)
}

private fun createLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return logging
}

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(HEADER_API_KEY, BuildConfig.MESSARI_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}