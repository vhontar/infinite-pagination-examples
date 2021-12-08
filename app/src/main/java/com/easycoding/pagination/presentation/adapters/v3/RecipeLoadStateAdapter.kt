package com.easycoding.pagination.presentation.adapters.v3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.pagination.databinding.RecyclerviewLoaderItemBinding

class RecipeLoaderStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder =
        LoaderViewHolder.from(parent, retry)
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) = holder.bind(loadState)
}

class LoaderViewHolder(
    private val binding: RecyclerviewLoaderItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Loading) {
            binding.mlLoader.transitionToEnd()
        } else {
            binding.mlLoader.transitionToStart()
        }
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = RecyclerviewLoaderItemBinding.inflate(inflater, parent, false)
            return LoaderViewHolder(view, retry)
        }
    }
}