package com.crypto.domain.repo

import com.crypto.domain.model.Currency

interface ICurrenciesRepo {
    suspend fun getCurrencies(page: Int): List<Currency>
}