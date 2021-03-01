package com.crypto.data.repo

import com.crypto.data.server.CurrencyApi
import com.crypto.domain.model.CurrencyMetric
import com.crypto.domain.model.CurrencyMetrics
import com.crypto.domain.model.CurrencyProfile
import com.crypto.domain.model.Link
import com.crypto.domain.repo.ICurrencyRepo
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CurrencyRepo(private val currencyApi: CurrencyApi): ICurrencyRepo {
    override suspend fun getCurrencyProfile(assetKey: String): CurrencyProfile {
        val overview = currencyApi.getCurrencyProfile(assetKey).data.profile.general.overview
        return CurrencyProfile(
            tagline = overview.tagline,
            projectDetails = overview.projectDetails,
            links = overview.officialLinks.map { link ->
                Link(
                    name = link.name,
                    link = link.link
                )
            }
        )
    }

    override suspend fun getCurrencyMetrics(assetKey: String): CurrencyMetrics {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
        }.time
        val start = dateFormat.format(startDate)
        val end = dateFormat.format(Date())
        val metrics = mutableListOf<CurrencyMetric>()

        currencyApi.getCurrencyMetrics(assetKey, start, end).data.values.forEach { batch ->
            val millis = batch[0]
            val subList = batch.subList(1, batch.lastIndex)
            val stepMillis = METRIC_TIME_INTERVAL / subList.size.toDouble()
            val elements = subList.mapIndexed { index, metric ->
                CurrencyMetric(
                    millis = millis.toLong() + ((index + 1) * stepMillis).toLong(),
                    value = metric
                )
            }
            metrics.addAll(elements)
        }
        return CurrencyMetrics(metrics)
    }

    companion object {
        private val METRIC_TIME_INTERVAL = TimeUnit.MINUTES.toMillis(30).toDouble()
    }
}