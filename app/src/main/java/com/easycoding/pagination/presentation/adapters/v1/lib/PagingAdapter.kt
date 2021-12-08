package com.easycoding.pagination.presentation.adapters.v1.lib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.databinding.IncludeProgressBarBinding
import com.easycoding.pagination.presentation.adapters.common.holders.Holder
import com.easycoding.pagination.presentation.adapters.common.holders.ProgressBarHolder
import com.easycoding.pagination.business.constants.AppConstants

abstract class PagingAdapter(
    private val items: ArrayList<Holder> = arrayListOf(),
    private val defaultItemsCount: Int = AppConstants.RECORD_LIMIT
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var fullyLoaded: Boolean = false
        private set
    var loading: Boolean = false
        private set
    private var loadMoreModeEnabled: Boolean = false

    val currentList: List<Holder>
        get() = items

    override fun getItemCount(): Int = items.size

    fun pushList(holders: List<Holder>) {
        if (loadMoreModeEnabled) {
            hideLoading()

            if (holders.isEmpty()) {
                fullyLoaded = true
                return
            }

            if (holders.size < defaultItemsCount) {
                fullyLoaded = true
            }

            val startPosition = items.size
            items.addAll(holders)
            notifyItemRangeInserted(startPosition, holders.size)
        } else {
            items.clear()
            items.addAll(holders)
            notifyDataSetChanged()
        }
    }

    fun showLoading() {
        // want to load more data
        loadMoreModeEnabled = true

        items.add(ProgressBarHolder())
        notifyItemInserted(items.size - 1)
        loading = true
    }

    fun hideLoading() {
        if (items.isNotEmpty() && items[items.size - 1] is ProgressBarHolder) {
            notifyItemRemoved(items.size - 1)
            items.removeAt(items.size - 1)
        }

        loading = false
    }

    fun reset() {
        loadMoreModeEnabled = false
        fullyLoaded = false
        pushList(ArrayList())
    }
}

class ProgressViewHolder private constructor(
    binding: IncludeProgressBarBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ProgressViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = IncludeProgressBarBinding.inflate(layoutInflater, parent, false)

            return ProgressViewHolder(binding)
        }
    }
}

/*
* You can create custom ItemType inside RecipeAdapter if you need more that ProgressBar and one item to show
* You will have to override getItemViewType method also
* */
enum class ItemType {
    PROGRESS_BAR,
    ITEM
}