package com.easycoding.pagination.business.network.abstraction

import com.easycoding.pagination.business.domain.model.Recipe

interface RecipeNetwork {
    suspend fun getRecipes(query: String, skip: Int, limit: Int): List<Recipe>
}