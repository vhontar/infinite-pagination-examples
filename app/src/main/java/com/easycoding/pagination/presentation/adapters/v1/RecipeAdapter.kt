package com.easycoding.pagination.presentation.adapters.v1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.databinding.RecyclerviewRecipeItemBinding
import com.easycoding.pagination.presentation.adapters.v1.lib.ItemType
import com.easycoding.pagination.presentation.adapters.v1.lib.PagingAdapter
import com.easycoding.pagination.presentation.adapters.v1.lib.ProgressViewHolder
import com.easycoding.pagination.utils.LoggerUtils
import com.easycoding.pagination.utils.loadUrl

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
                is RecipeViewHolder -> holder.bind(itemsList[position] as Recipe)
            }
        } catch (e: Throwable) {
            LoggerUtils.logException(e)
        }
    }
}

class RecipeViewHolder(
    private val binding: RecyclerviewRecipeItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: Recipe) {
        binding.item = recipe
        binding.ivRecipe.loadUrl(recipe.imageUrl)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): RecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewRecipeItemBinding.inflate(inflater, parent, false)
            return RecipeViewHolder(binding)
        }
    }
}