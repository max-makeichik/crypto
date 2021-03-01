package com.crypto.ui.currency

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crypto.R
import com.crypto.ui.base.BaseFragment
import com.crypto.ui.navigation.NavigationConstants
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CurrencyFragment : BaseFragment() {

    override val layoutResId = R.layout.fragment_currency

    private val viewModel by viewModel<CurrencyViewModel> {
        parametersOf(requireArguments().getParcelable(NavigationConstants.CURRENCY_MODEL))
    }

    private val linksAdapter by lazy { LinksAdapter() }

    private lateinit var toolbar: Toolbar
    private lateinit var symbolLabel: TextView
    private lateinit var nameLabel: TextView
    private lateinit var priceUsdLabel: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var taglineLabel: TextView
    private lateinit var detailsLabel: TextView
    private lateinit var linksRecyclerView: RecyclerView
    private lateinit var chartView: LineChart
    private lateinit var chartTimeFrame: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
        symbolLabel = view.findViewById(R.id.symbol)
        nameLabel = view.findViewById(R.id.name)
        priceUsdLabel = view.findViewById(R.id.price_usd)
        progressBar = view.findViewById(R.id.progress_bar)
        taglineLabel = view.findViewById(R.id.tagline)
        detailsLabel = view.findViewById(R.id.details)
        linksRecyclerView = view.findViewById<RecyclerView>(R.id.links_recycler).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = linksAdapter
        }
        chartView = view.findViewById(R.id.currency_line_chart)
        chartView.setNoDataText("")
        chartTimeFrame = view.findViewById(R.id.chart_time_frame)

        viewModel.currencyData.observe(this, { model ->
            val currency = model.currency
            toolbar.title = currency.name
            symbolLabel.text = currency.symbol
            nameLabel.text = currency.name
            priceUsdLabel.text = currency.usdPrice
            taglineLabel.text = model.profile?.tagline

            progressBar.isVisible = model.profile == null

            model.profile?.projectDetails?.let {
                detailsLabel.apply {
                    text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    movementMethod = LinkMovementMethod.getInstance()
                }
            }
            model.profile?.links?.let { linksAdapter.items = it }
            model.metrics?.let { showChart(it) }
        })
    }

    private fun showChart(metrics: CurrencyMetricsUiModel) {
        chartView.isVisible = true
        chartTimeFrame.isVisible = true
        val dataSet = LineDataSet(metrics.entries, "test!")
        styleAxis()
        styleData(dataSet)
        chartView.apply {
            description = Description().apply {
                text = ""
            }
            legend.isEnabled = false
            data = LineData(dataSet)
            invalidate()
        }
    }

    private fun styleAxis() {
        val xAxis = chartView.xAxis
        xAxis.apply {
            setDrawLabels(true)
            position = XAxis.XAxisPosition.TOP
            setDrawGridLines(false)
            setDrawLabels(false)
        }
        chartView.axisRight.apply {
            isEnabled = false
        }
    }

    private fun styleData(dataSet: LineDataSet) {
        dataSet.apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawCircles(false)
            setDrawValues(false)
            valueTextSize = 11f
            setDrawFilled(true)
        }
    }
}