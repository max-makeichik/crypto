package com.crypto.data.server

import com.crypto.data.model.CurrenciesResponse
import com.crypto.data.model.CurrencyMetricsResponse
import com.crypto.data.model.CurrencyProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {

    companion object {
        const val API_PREFIX_V1 = "api/v1/"
        const val API_PREFIX_V2 = "api/v2/"
    }

    @GET("${API_PREFIX_V2}assets")
    suspend fun getCurrencies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("fields") fields: String = ""
    ): CurrenciesResponse

    @GET("${API_PREFIX_V2}assets/{assetKey}/profile")
    suspend fun getCurrencyProfile(@Path("assetKey") assetKey: String): CurrencyProfileResponse

    @GET("${API_PREFIX_V1}assets/{assetKey}/metrics/price/time-series")
    suspend fun getCurrencyMetrics(
        @Path("assetKey") assetKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): CurrencyMetricsResponse
}