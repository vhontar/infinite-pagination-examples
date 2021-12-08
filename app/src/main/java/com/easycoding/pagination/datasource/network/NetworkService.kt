package com.easycoding.pagination.datasource.network

import com.easycoding.pagination.datasource.network.entities.GetRecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/search")
    suspend fun fetchRecipes(
        @Query("q") query: String,
        @Query("from") skip: Int,
        @Query("to") limit: Int
    ): Response<GetRecipesResponse>
}