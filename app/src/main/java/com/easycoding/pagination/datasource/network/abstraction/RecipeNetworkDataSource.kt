package com.easycoding.pagination.datasource.network.abstraction

import com.easycoding.pagination.business.domain.model.Recipe

interface RecipeNetworkDataSource {
    suspend fun getRecipes(query: String, skip: Int, linit: Int): List<Recipe>
}