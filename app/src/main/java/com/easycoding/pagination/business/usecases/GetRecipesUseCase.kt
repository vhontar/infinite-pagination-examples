package com.easycoding.pagination.business.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.easycoding.pagination.business.constants.AppConstants
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.data.network.abstraction.RecipeNetwork
import com.easycoding.pagination.business.data.paging.RecipePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeNetwork: RecipeNetwork
) {
    suspend fun fetchRecipes(query: String, skip: Int, limit: Int): List<Recipe> {
        // we can handle result here and return DataState to viewModel
        // for simplicity
        return recipeNetwork.fetchRecipes(query, skip, limit)
    }

    fun getRecipesAsPagingData(query: String): Flow<PagingData<Recipe>> {
        val pagingConfig = PagingConfig(pageSize = AppConstants.RECORD_LIMIT, enablePlaceholders = true)
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { RecipePagingSource(recipeNetwork, query) }
        ).flow
    }
}