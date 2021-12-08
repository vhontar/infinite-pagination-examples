package com.easycoding.pagination.presentation.adapters.v2.lib

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val adapter: PagingAdapter,
    private val loadMore: () -> Unit
): RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!adapter.fullyLoaded && !adapter.loading && isLastItem(layoutManager)) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount > 0) {
                adapter.showLoading()
                loadMore.invoke()
            }
        }
    }

    private fun isLastItem(layoutManager: LinearLayoutManager): Boolean {
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        return lastVisiblePosition == adapter.itemCount - 1
    }
}