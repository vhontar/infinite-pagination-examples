package com.easycoding.pagination.business.data.network.abstraction

import com.easycoding.pagination.business.domain.model.Recipe

interface RecipeNetwork {
    suspend fun fetchRecipes(query: String, skip: Int, limit: Int): List<Recipe>
}