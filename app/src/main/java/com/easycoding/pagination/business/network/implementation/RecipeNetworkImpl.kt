package com.easycoding.pagination.business.network.implementation

import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.network.abstraction.RecipeNetwork
import com.easycoding.pagination.datasource.network.abstraction.RecipeNetworkDataSource
import javax.inject.Inject

class RecipeNetworkImpl @Inject constructor(
    private val recipeNetworkDataSource: RecipeNetworkDataSource
): RecipeNetwork {
    override suspend fun getRecipes(query: String, skip: Int, limit: Int): List<Recipe> {
        return recipeNetworkDataSource.getRecipes(query, skip, limit)
    }
}