package com.easycoding.pagination.datasource.network.implementation

import android.content.Context
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.datasource.network.NetworkService
import com.easycoding.pagination.datasource.network.abstraction.RecipeNetworkDataSource
import com.easycoding.pagination.datasource.network.entities.toDomainModels
import com.easycoding.pagination.datasource.network.utils.NetworkResult
import com.easycoding.pagination.datasource.network.utils.safeApiCall
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RecipeNetworkDataSourceImpl @Inject constructor(
    private val networkService: NetworkService,
    private val context: Context
): RecipeNetworkDataSource {
    override suspend fun getRecipes(query: String, skip: Int, limit: Int): List<Recipe> {
        val response = safeApiCall(context, Dispatchers.IO) {
            networkService.getRecipes(query, skip, limit)
        }

        return when (response) {
            is NetworkResult.Success -> {
                if (response.value?.isSuccessful == true) {
                    response.value.body()?.hits?.mapNotNull { it.recipeEntity }?.toDomainModels() ?: listOf()
                } else {
                    // for simplicity
                    listOf()
                }
            }
            // for simplicity
            else -> listOf()
        }
    }
}