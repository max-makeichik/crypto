package com.crypto.ui.currencies

import androidx.lifecycle.MutableLiveData
import com.crypto.domain.usecase.GetCurrenciesUseCase
import com.crypto.ui.base.BaseViewModel
import com.crypto.ui.base.DiffItem
import com.crypto.ui.base.ProgressItemUIModel
import com.crypto.ui.mapper.CurrencyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.CancellationException

class CurrenciesViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val currencyMapper: CurrencyMapper
) : BaseViewModel() {

    var currenciesData: MutableLiveData<List<DiffItem>> = MutableLiveData(listOf())

    private var currenciesLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var currentPage: MutableLiveData<Int> = MutableLiveData(1)

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        if (currenciesLoading.value == true) return

        currenciesLoading.value = true

        var currencies = currenciesData.value!!.toMutableList()
        attachedScope.launch {
            try {
                if (currencies.isEmpty() || currencies.last() !is ProgressItemUIModel) {
                    currencies.add(ProgressItemUIModel())
                }
                currenciesData.postValue(currencies)

                withContext(Dispatchers.IO) {
                    val result = currencyMapper.mapFrom(
                        getCurrenciesUseCase.getCurrencies(currentPage.value!!)
                    )
                    currencies = currenciesData.value!!.toMutableList()
                    if (currencies.lastOrNull() is ProgressItemUIModel) {
                        currencies.removeLast()
                    }
                    currencies.addAll(result)

                    currenciesData.postValue(currencies)
                }
                currentPage.value = currentPage.value!! + 1
            } catch (e: Throwable) {
                if (e !is CancellationException) {
                    Timber.e(e)
                }
                currencies = currenciesData.value!!.toMutableList()
                if (currencies.lastOrNull() is ProgressItemUIModel) {
                    currencies.removeLast()
                }

                currenciesData.postValue(currencies)
            } finally {
                currenciesLoading.value = false
            }
        }
    }
}