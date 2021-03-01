package com.crypto.domain.usecase

import com.crypto.domain.model.Currency
import com.crypto.domain.repo.ICurrenciesRepo

class GetCurrenciesUseCase(private val currenciesRepo: ICurrenciesRepo) {

    suspend fun getCurrencies(nextPage: Int): List<Currency> {
        return currenciesRepo.getCurrencies(nextPage)
    }

}