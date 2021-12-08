package com.easycoding.pagination.business.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easycoding.pagination.business.constants.AppConstants
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.data.network.abstraction.RecipeNetwork
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(
    private val recipeNetwork: RecipeNetwork,
    private val query: String
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val skip = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = recipeNetwork.fetchRecipes(query, skip, skip + AppConstants.RECORD_LIMIT)
            LoadResult.Page(
                data = response,
                prevKey = if (skip == DEFAULT_PAGE_INDEX) null else skip - AppConstants.RECORD_LIMIT,
                nextKey = if (response.isEmpty()) null else skip + AppConstants.RECORD_LIMIT
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? = null

    companion object {
        private const val DEFAULT_PAGE_INDEX = 0
    }
}