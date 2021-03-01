package com.crypto.ui.currency

import androidx.lifecycle.MutableLiveData
import com.crypto.domain.usecase.GetCurrencyUseCase
import com.crypto.ui.base.BaseViewModel
import com.crypto.ui.currencies.CurrencyItemUIModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.EntryXComparator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.ArrayList
import java.util.concurrent.CancellationException

class CurrencyViewModel(
    currency: CurrencyItemUIModel,
    private val getCurrencyUseCase: GetCurrencyUseCase
) : BaseViewModel() {

    var currencyData: MutableLiveData<CurrencyUiModel> = MutableLiveData(
        CurrencyUiModel(currency)
    )

    init {
        loadCurrency()
    }

    private fun loadCurrency() {
        attachedScope.launch {
            try {
                val currencyId = currencyData.value!!.currency.id
                val profileAsync = async {
                    withContext(Dispatchers.IO) {
                        getCurrencyUseCase.getCurrencyProfile(currencyId)
                    }
                }
                val metricsAsync = async {
                    withContext(Dispatchers.IO) {
                        getCurrencyUseCase.getCurrencyMetrics(currencyId)
                    }
                }
                var currencyModel = currencyData.value!!
                val currencyProfile = with(profileAsync.await()) {
                    CurrencyProfileUiModel(
                        tagline = tagline,
                        projectDetails = projectDetails,
                        links = links.map { link ->
                            LinkItemUIModel(
                                name = link.name,
                                link = link.link
                            )
                        }
                    )
                }
                val currencyMetrics = with(metricsAsync.await()) {
                    val entries = ArrayList<Entry>()
                    metrics.mapTo(entries) { metric ->
                        Entry(metric.millis.toFloat(), metric.value.toFloat())
                    }
                    entries.sortWith(EntryXComparator())
                    CurrencyMetricsUiModel(entries)
                }
                currencyModel = currencyModel.copy(
                    profile = currencyProfile,
                    metrics = currencyMetrics
                )
                currencyData.postValue(currencyModel)
            } catch (e: Throwable) {
                if (e !is CancellationException) {
                    Timber.e(e)
                }
            }
        }
    }
}