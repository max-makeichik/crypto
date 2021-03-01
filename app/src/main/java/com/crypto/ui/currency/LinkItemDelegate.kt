package com.crypto.ui.currency

import android.os.Parcelable
import android.widget.TextView
import com.crypto.R
import com.crypto.ui.base.DiffItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.parcel.Parcelize

fun linkItemDelegate() = adapterDelegateLayoutContainer<LinkItemUIModel, DiffItem>(R.layout.item_link) {

        val nameLabel: TextView = itemView.findViewById(R.id.name)
        val linkLabel: TextView = itemView.findViewById(R.id.link)

        bind {
            nameLabel.text = item.name
            linkLabel.text = item.link
        }
    }

@Parcelize
data class LinkItemUIModel(
    val name: String,
    val link: String
) : DiffItem, Parcelable {
    override fun areItemsTheSame(newItem: DiffItem): Boolean =
        newItem is LinkItemUIModel && this.name == newItem.name

    override fun areContentsTheSame(newItem: DiffItem): Boolean =
        newItem is LinkItemUIModel
                && this.name == newItem.name
                && this.link == newItem.link
}