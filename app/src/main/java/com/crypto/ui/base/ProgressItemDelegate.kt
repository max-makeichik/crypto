package com.crypto.ui.base

import com.crypto.R
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

fun progressItemDelegate() = adapterDelegateLayoutContainer<ProgressItemUIModel, DiffItem>(R.layout.item_progress) {
}

class ProgressItemUIModel : DiffItem {
    override fun areItemsTheSame(newItem: DiffItem): Boolean =
        newItem is ProgressItemUIModel

    override fun areContentsTheSame(newItem: DiffItem): Boolean =
        newItem is ProgressItemUIModel
}