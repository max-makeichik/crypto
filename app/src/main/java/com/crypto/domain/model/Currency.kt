package com.crypto.domain.model

data class Currency(
    val id: String,
    val symbol: String,
    val name: String,
    val usdPrice: Double
)