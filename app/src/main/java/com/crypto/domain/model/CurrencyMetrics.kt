package com.crypto.domain.model

data class CurrencyMetrics(
    val metrics: List<CurrencyMetric>
)

data class CurrencyMetric(
    val millis: Long,
    val value: Double
)