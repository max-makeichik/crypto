package com.crypto.domain.usecase

import com.crypto.domain.model.CurrencyMetrics
import com.crypto.domain.model.CurrencyProfile
import com.crypto.domain.repo.ICurrencyRepo

class GetCurrencyUseCase(private val currencyRepo: ICurrencyRepo) {

    suspend fun getCurrencyProfile(slug: String): CurrencyProfile {
        return currencyRepo.getCurrencyProfile(slug)
    }

    suspend fun getCurrencyMetrics(slug: String): CurrencyMetrics {
        return currencyRepo.getCurrencyMetrics(slug)
    }
}