package com.easycoding.pagination.business.data.network.implementation

import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.data.network.abstraction.RecipeNetwork
import com.easycoding.pagination.datasource.network.abstraction.RecipeNetworkDataSource
import javax.inject.Inject

class RecipeNetworkImpl @Inject constructor(
    private val recipeNetworkDataSource: RecipeNetworkDataSource
): RecipeNetwork {
    override suspend fun fetchRecipes(query: String, skip: Int, limit: Int): List<Recipe> {
        return recipeNetworkDataSource.fetchRecipes(query, skip, limit)
    }
}