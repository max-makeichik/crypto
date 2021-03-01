package com.crypto.data.repo

import com.crypto.data.server.CurrencyApi
import com.crypto.domain.model.Currency
import com.crypto.domain.repo.ICurrenciesRepo

class CurrenciesRepo(
    private val api: CurrencyApi
) : ICurrenciesRepo {

    override suspend fun getCurrencies(page: Int): List<Currency> {
        val currencies = api.getCurrencies(
            page = page,
            limit = CURRENCY_LIMIT,
            fields = CURRENCY_FIELDS
        )
        return currencies.data.map { data ->
            Currency(
                id = data.id,
                symbol = data.symbol,
                name = data.name,
                usdPrice = data.metrics.marketData.priceUsd
            )
        }
    }

    companion object {
        private const val CURRENCY_FIELDS = "id,symbol,name,metrics/market_data/price_usd"
        private const val CURRENCY_LIMIT = 20
    }
}