package com.crypto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrenciesResponse(
    val data: List<Data>
)

@JsonClass(generateAdapter = true)
data class Data(
    val id: String,
    val metrics: Metrics,
    val name: String,
    val symbol: String
)

@JsonClass(generateAdapter = true)
data class Metrics(
    @Json(name = "market_data") val marketData: MarketData
)

@JsonClass(generateAdapter = true)
data class MarketData(
    @Json(name = "price_usd") val priceUsd: Double
)