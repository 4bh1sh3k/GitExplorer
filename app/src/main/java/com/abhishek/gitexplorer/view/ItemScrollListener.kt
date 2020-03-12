package com.abhishek.gitexplorer.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val THRESHOLD_DEFAULT = 5

class LoadOnScrollListener(
    private val threshold: Int = THRESHOLD_DEFAULT,
    private val onBeginLoad: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoading = false

    fun setLoadComplete() {
        isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!isLoading) {
            val manager = recyclerView.layoutManager as? LinearLayoutManager ?: return

            val remaining = manager.itemCount - manager.findLastVisibleItemPosition()
            if (remaining < threshold) {
                isLoading = true
                onBeginLoad()
            }
        }
    }
}