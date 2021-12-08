package com.easycoding.pagination.datasource.network.entities

import com.google.gson.annotations.SerializedName

data class WrapperRecipeEntity(
    @SerializedName("recipe") val recipeEntity: RecipeEntity? = null
)