package com.abhishek.gitexplorer.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.gitexplorer.view.ItemAdapter
import com.abhishek.gitexplorer.viewmodel.HomeViewModel

@BindingAdapter("isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun setItems(view: RecyclerView, items: ObservableList<HomeViewModel.ItemViewModel>) {
    view.adapter = ItemAdapter(items)
}