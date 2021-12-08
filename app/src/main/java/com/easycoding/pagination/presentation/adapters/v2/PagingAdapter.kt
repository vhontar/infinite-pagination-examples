package com.easycoding.pagination.presentation.adapters.v2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.databinding.IncludeProgressBarBinding
import com.easycoding.pagination.presentation.adapters.v1.lib.holder.Holder
import com.easycoding.pagination.presentation.adapters.v1.lib.holder.ProgressBarHolder

//abstract class PagingAdapter<T : RecyclerView.ViewHolder>(
//    private val items: ArrayList<Holder>,
//    private val defaultItemsCount: Int
//) : ListAdapter<Holder, T>() {
//    var fullyLoaded: Boolean = false
//        private set
//    var loading: Boolean = false
//        private set
//    private var loadMoreModeEnabled: Boolean = false
//
//    val itemsCount: Int
//        get() = items.size
//
//    fun submitList(holders: List<Holder>) {
//        if (loadMoreModeEnabled) {
//            hideLoading()
//
//            if (holders.isEmpty()) {
//                fullyLoaded = true
//                return
//            }
//
//            if (holders.size < defaultItemsCount) {
//                fullyLoaded = true
//            }
//
//            val startPosition = items.size
//            items.addAll(holders)
//            notifyItemRangeInserted(startPosition, holders.size)
//        } else {
//            items.clear()
//            items.addAll(holders)
//            notifyDataSetChanged()
//        }
//    }
//
//    fun showLoading() {
//        // show that we want to load more data
//        loadMoreModeEnabled = true
//
//        items.add(ProgressBarHolder())
//        notifyItemInserted(items.size - 1)
//        loading = true
//    }
//
//    fun hideLoading() {
//        if (items.isNotEmpty() && items[items.size - 1] is ProgressBarHolder) {
//            notifyItemRemoved(items.size - 1)
//            items.removeAt(items.size - 1)
//        }
//
//        loading = false
//    }
//
//    fun reset() {
//        loadMoreModeEnabled = false
//        fullyLoaded = false
//        submitList(ArrayList())
//    }
//}
//
//internal class PagingHolderDiffUtilCallback: DiffUtil.ItemCallback<Holder>() {
//
//}
//
//class ProgressViewHolder private constructor(
//    binding: IncludeProgressBarBinding
//) : RecyclerView.ViewHolder(binding.root) {
//
//    companion object {
//        fun from(parent: ViewGroup): ProgressViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = IncludeProgressBarBinding.inflate(layoutInflater, parent, false)
//
//            return ProgressViewHolder(binding)
//        }
//    }
//}
//
//enum class ItemType {
//    PROGRESS_BAR,
//    ITEM
//}