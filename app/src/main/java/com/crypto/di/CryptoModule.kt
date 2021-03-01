package com.crypto.di

import com.crypto.data.repo.CurrenciesRepo
import com.crypto.data.repo.CurrencyRepo
import com.crypto.domain.repo.ICurrenciesRepo
import com.crypto.domain.repo.ICurrencyRepo
import com.crypto.domain.usecase.GetCurrenciesUseCase
import com.crypto.domain.usecase.GetCurrencyUseCase
import com.crypto.ui.currencies.CurrenciesViewModel
import com.crypto.ui.currencies.CurrencyItemUIModel
import com.crypto.ui.currency.CurrencyViewModel
import com.crypto.ui.mapper.CurrencyMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currenciesModule = module {
    single { GetCurrenciesUseCase(get()) }
    single { CurrenciesRepo(get()) as ICurrenciesRepo }
    single { CurrencyMapper() }
    viewModel { CurrenciesViewModel(get(), get()) }
}

val currencyModule = module {
    single { GetCurrencyUseCase(get()) }
    single { CurrencyRepo(get()) as ICurrencyRepo }
    viewModel { (currency : CurrencyItemUIModel) -> CurrencyViewModel(currency, get()) }
}

val cryptoApp = listOf(
    currenciesModule, currencyModule, serverModule
)