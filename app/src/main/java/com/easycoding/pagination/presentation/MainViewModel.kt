package com.easycoding.pagination.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easycoding.pagination.business.constants.AppConstants
import com.easycoding.pagination.business.domain.model.Recipe
import com.easycoding.pagination.business.usecases.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private var skipRecipes = 0

    fun getRecipes(query: String? = null) = viewModelScope.launch {
        Log.d(AppConstants.APP_TAG, "getRecipes called: $skipRecipes")
        // first load
        if (skipRecipes == 0) {
            _dataLoading.value = true
        }
        val recipes = getRecipesUseCase.getRecipes(
            query ?: "chicken",
            skipRecipes,
            skipRecipes + AppConstants.RECORD_LIMIT
        )

        _recipes.value = recipes
        skipRecipes += recipes.size
        _dataLoading.value = false
    }
}