package com.crypto.ui.base

interface DiffItem {
    fun areItemsTheSame(newItem: DiffItem): Boolean

    fun areContentsTheSame(newItem: DiffItem): Boolean
}