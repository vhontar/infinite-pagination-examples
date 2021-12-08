package com.easycoding.pagination.datasource.network.abstraction

import com.easycoding.pagination.business.domain.model.Recipe

interface RecipeNetworkDataSource {
    suspend fun fetchRecipes(query: String, skip: Int, linit: Int): List<Recipe>
}