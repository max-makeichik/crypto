package com.crypto.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crypto.R
import com.crypto.ui.base.BaseFragment
import com.crypto.ui.navigation.NavigationConstants
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrenciesFragment : BaseFragment() {

    override val layoutResId = R.layout.fragment_currencies

    private val viewModel by viewModel<CurrenciesViewModel>()

    private val currenciesAdapter by lazy {
        CurrenciesAdapter {
            val bundle = bundleOf(NavigationConstants.CURRENCY_MODEL to it)
            findNavController().navigate(R.id.action_CurrenciesFragment_to_CurrencyFragment, bundle)
        }
    }

    private val currenciesScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val pos = linearLayoutManager.findLastVisibleItemPosition()
            if (pos == currenciesAdapter.itemCount - 1) {
                viewModel.loadCurrencies()
            }
        }
    }

    private val itemDecoration by lazy {
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.divider_drawable,
                    context!!.theme
                )!!
            )
        }
    }

    private lateinit var toolbar: Toolbar
    private lateinit var currenciesRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById<Toolbar>(R.id.toolbar).apply {
            title = getString(R.string.app_name)
        }
        currenciesRecyclerView = view.findViewById<RecyclerView>(R.id.currencies_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = currenciesAdapter
            addOnScrollListener(currenciesScrollListener)
        }

        viewModel.currenciesData.observe(this, {
            currenciesAdapter.items = it
            if (currenciesAdapter.items.size > 1 && currenciesRecyclerView.itemDecorationCount == 0) {
                currenciesRecyclerView.addItemDecoration(itemDecoration)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currenciesRecyclerView.removeOnScrollListener(currenciesScrollListener)
    }
}