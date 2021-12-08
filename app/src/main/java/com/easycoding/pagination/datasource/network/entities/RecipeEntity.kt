package com.easycoding.pagination.datasource.network.entities

import com.easycoding.pagination.business.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeEntity(
    @SerializedName("label") val label: String? = null,
    @SerializedName("image") val imageUrl: String? = null
)

// it can be datasource may know about domain models
fun RecipeEntity.toDomainModel(): Recipe = Recipe(
    label = label ?: "",
    imageUrl = imageUrl ?: ""
)

fun List<RecipeEntity>.toDomainModels(): List<Recipe> = map { it.toDomainModel() }