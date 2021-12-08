package com.easycoding.pagination.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.easycoding.pagination.R
import com.easycoding.pagination.databinding.ActivityMainBinding
import com.easycoding.pagination.presentation.adapters.v1.RecipeAdapter
import com.easycoding.pagination.presentation.adapters.v1.lib.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val adapterV1 = RecipeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        implementV1Adapter()
    }

    private fun implementV1Adapter() {
        binding.rvRecipes.adapter = adapterV1
        binding.rvRecipes.addOnScrollListener(PaginationScrollListener(adapterV1) {
            viewModel.getRecipes()
        })

        viewModel.getRecipes()
        viewModel.recipes.observe(this) {
            adapterV1.submitList(it)
        }
    }

    private fun implementV2Adapter() {

    }

    private fun implementV3Adapter() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}