package com.easycoding.pagination.presentation.adapters.v3

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.presentation.adapters.common.viewholders.RecipeViewHolder

class RecipeAdapter: PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = RecipeViewHolder.from(parent)
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(getItem(position))
}

class RecipeDiffUtilCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.imageUrl == newItem.imageUrl
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem == newItem
}