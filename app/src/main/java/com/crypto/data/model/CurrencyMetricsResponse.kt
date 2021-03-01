package com.crypto.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyMetricsResponse(
    val data: Data
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        val values: List<List<Double>>
    )
}