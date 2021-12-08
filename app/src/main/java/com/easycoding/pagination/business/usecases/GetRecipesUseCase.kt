package com.easycoding.pagination.business.usecases

import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.network.abstraction.RecipeNetwork
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeNetwork: RecipeNetwork
) {
    suspend fun getRecipes(query: String, skip: Int, limit: Int): List<Recipe> {
        // we can handle result here and return DataState to viewModel
        // for simplicity
        return recipeNetwork.getRecipes(query, skip, limit)
    }
}