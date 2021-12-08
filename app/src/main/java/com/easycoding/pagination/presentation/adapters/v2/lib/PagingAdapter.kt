package com.easycoding.pagination.presentation.adapters.v2.lib

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.business.constants.AppConstants
import com.easycoding.pagination.databinding.IncludeProgressBarBinding
import com.easycoding.pagination.presentation.adapters.common.holders.Holder
import com.easycoding.pagination.presentation.adapters.common.holders.ProgressBarHolder
import com.easycoding.pagination.utils.copy

abstract class PagingAdapter(
    private val defaultItemsCount: Int = AppConstants.RECORD_LIMIT
) : ListAdapter<Holder, RecyclerView.ViewHolder>(PagingHolderDiffUtilCallback()) {
    var fullyLoaded: Boolean = false
        private set
    var loading: Boolean = false
        private set
    private var loadMoreModeEnabled: Boolean = false

    fun pushList(holders: List<Holder>) {
        if (loadMoreModeEnabled) {
            loading = false

            if (holders.isEmpty()) {
                fullyLoaded = true
                return
            }

            if (holders.size < defaultItemsCount) {
                fullyLoaded = true
            }

            val newList = currentList.copy()
            if (newList.isNotEmpty() && newList[newList.size - 1] is ProgressBarHolder) {
                newList.removeAt(newList.size - 1)
            }
            newList.addAll(holders)
            submitList(newList)
        } else {
            submitList(holders)
        }
    }

    fun showLoading() {
        // show that we want to load more data
        loadMoreModeEnabled = true

        val items = currentList.copy()
        items.add(ProgressBarHolder())
        submitList(items)
        loading = true
    }

    fun reset() {
        loadMoreModeEnabled = false
        fullyLoaded = false
        submitList(ArrayList())
    }
}

class PagingHolderDiffUtilCallback : DiffUtil.ItemCallback<Holder>() {
    override fun areItemsTheSame(oldItem: Holder, newItem: Holder): Boolean = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Holder, newItem: Holder): Boolean = oldItem == newItem
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

enum class ItemType {
    PROGRESS_BAR,
    ITEM
}