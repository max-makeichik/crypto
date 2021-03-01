package com.crypto.ui.currencies

import android.os.Parcelable
import android.widget.TextView
import com.crypto.R
import com.crypto.ui.base.DiffItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.parcel.Parcelize

fun currencyItemDelegate(onCurrencyClick: (CurrencyItemUIModel) -> Unit) =
    adapterDelegateLayoutContainer<CurrencyItemUIModel, DiffItem>(R.layout.item_currency) {

        val symbolLabel: TextView = itemView.findViewById(R.id.symbol)
        val nameLabel: TextView = itemView.findViewById(R.id.name)
        val priceUsdLabel: TextView = itemView.findViewById(R.id.price_usd)

        bind {
            itemView.setOnClickListener { onCurrencyClick(item) }
            symbolLabel.text = item.symbol
            nameLabel.text = item.name
            priceUsdLabel.text = item.usdPrice
        }
    }

@Parcelize
data class CurrencyItemUIModel(
    val id: String,
    val symbol: String,
    val name: String,
    val usdPrice: String
) : DiffItem, Parcelable {
    override fun areItemsTheSame(newItem: DiffItem): Boolean =
        newItem is CurrencyItemUIModel && this.name == newItem.name

    override fun areContentsTheSame(newItem: DiffItem): Boolean =
        newItem is CurrencyItemUIModel
                && this.symbol == newItem.symbol
                && this.name == newItem.name
                && this.usdPrice == newItem.usdPrice
}