package com.easycoding.pagination.presentation.adapters.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.databinding.RecyclerviewRecipeItemBinding
import com.easycoding.pagination.presentation.adapters.common.holders.RecipeHolder
import com.easycoding.pagination.utils.loadUrl

class RecipeViewHolder(
    private val binding: RecyclerviewRecipeItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(holder: RecipeHolder?) {
        bind(holder?.recipe)
    }

    fun bind(recipe: Recipe?) {
        if (recipe == null)
            return

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