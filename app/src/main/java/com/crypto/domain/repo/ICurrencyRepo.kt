package com.crypto.domain.repo

import com.crypto.domain.model.CurrencyMetrics
import com.crypto.domain.model.CurrencyProfile

interface ICurrencyRepo {
    suspend fun getCurrencyProfile(assetKey: String): CurrencyProfile
    suspend fun getCurrencyMetrics(assetKey: String): CurrencyMetrics
}