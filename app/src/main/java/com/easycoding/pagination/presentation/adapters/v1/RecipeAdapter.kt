package com.easycoding.pagination.presentation.adapters.v1

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.presentation.adapters.common.holders.RecipeHolder
import com.easycoding.pagination.presentation.adapters.common.viewholders.RecipeViewHolder
import com.easycoding.pagination.presentation.adapters.v1.lib.ItemType
import com.easycoding.pagination.presentation.adapters.v1.lib.PagingAdapter
import com.easycoding.pagination.presentation.adapters.v1.lib.ProgressViewHolder
import com.easycoding.pagination.utils.LoggerUtils

class RecipeAdapter: PagingAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ItemType.ITEM.ordinal -> RecipeViewHolder.from(parent)
            else -> ProgressViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // if items doesn't have position or type doesn't match to Recipe
        try {
            when (holder) {
                is RecipeViewHolder -> holder.bind(currentList[position] as RecipeHolder)
            }
        } catch (e: Throwable) {
            LoggerUtils.logException(e)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is RecipeHolder -> ItemType.ITEM.ordinal
            else -> ItemType.PROGRESS_BAR.ordinal
        }
    }
}