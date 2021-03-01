package com.crypto.ui.currency

import com.crypto.ui.currencies.CurrencyItemUIModel
import com.github.mikephil.charting.data.Entry

data class CurrencyUiModel(
    val currency: CurrencyItemUIModel,
    val profile: CurrencyProfileUiModel? = null,
    val metrics: CurrencyMetricsUiModel? = null
)

data class CurrencyProfileUiModel(
    val tagline: String,
    val projectDetails: String,
    val links: List<LinkItemUIModel>
)

data class CurrencyMetricsUiModel(
    val entries: List<Entry>
)