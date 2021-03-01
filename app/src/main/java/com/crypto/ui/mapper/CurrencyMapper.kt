package com.crypto.ui.mapper

import com.crypto.domain.model.Currency
import com.crypto.ui.currencies.CurrencyItemUIModel
import java.text.NumberFormat

class CurrencyMapper {

    fun mapFrom(currencies: List<Currency>): List<CurrencyItemUIModel> {
        val format = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = java.util.Currency.getInstance(CURRENCY_USD)
        }
        return currencies.map { currency ->
            val usdPrice = format.format(currency.usdPrice)
            CurrencyItemUIModel(
                id = currency.id,
                symbol = currency.symbol,
                name = currency.name,
                usdPrice = usdPrice.replace(CURRENCY_USD, "$")
            )
        }
    }

    companion object {
        private const val CURRENCY_USD = "USD"
    }
}