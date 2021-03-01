package com.crypto.ui.currency

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crypto.ui.base.BaseDiffCallback
import com.crypto.ui.base.DiffItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class LinksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<DiffItem>
        get() = adapter.items
        set(value) {
            adapter.items = value
        }

    private val adapterDelegatesManager: AdapterDelegatesManager<List<DiffItem>> =
        AdapterDelegatesManager<List<DiffItem>>().apply {
            addDelegate(linkItemDelegate())
        }

    private val adapter: AsyncListDifferDelegationAdapter<DiffItem> =
        AsyncListDifferDelegationAdapter(BaseDiffCallback(), adapterDelegatesManager)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapter.onCreateViewHolder(parent, viewType)

    override fun getItemCount(): Int = adapter.itemCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapter.onBindViewHolder(holder, position)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) =
        adapter.onBindViewHolder(holder, position, payloads)

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
        adapter.unregisterAdapterDataObserver(observer)

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) =
        adapter.onViewDetachedFromWindow(holder)

    override fun getItemId(position: Int): Long = adapter.getItemId(position)

    override fun setHasStableIds(hasStableIds: Boolean) = adapter.setHasStableIds(hasStableIds)

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean =
        adapter.onFailedToRecycleView(holder)

    override fun getItemViewType(position: Int): Int = adapter.getItemViewType(position)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
        adapter.onAttachedToRecyclerView(recyclerView)

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) =
        adapter.onDetachedFromRecyclerView(recyclerView)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) = adapter.onViewRecycled(holder)

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) =
        adapter.registerAdapterDataObserver(observer)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) =
        adapter.onViewAttachedToWindow(holder)
}