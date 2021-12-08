package com.easycoding.pagination.datasource.network.entities

import com.google.gson.annotations.SerializedName

data class GetRecipesResponse(
    @SerializedName("hits") val hits: List<WrapperRecipeEntity>? = null
): DefaultResponse()