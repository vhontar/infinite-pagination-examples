package com.easycoding.pagination.business.domain.model

import com.easycoding.pagination.presentation.adapters.common.holders.Holder

data class Recipe(
    val label: String,
    val imageUrl: String
): Holder
