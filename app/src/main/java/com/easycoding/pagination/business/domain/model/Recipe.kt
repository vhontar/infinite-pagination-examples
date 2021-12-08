package com.easycoding.pagination.business.domain.model

import com.easycoding.pagination.presentation.adapters.v1.lib.holder.Holder

data class Recipe(
    val label: String,
    val imageUrl: String
): Holder
