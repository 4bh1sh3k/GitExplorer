package com.abhishek.gitexplorer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.gitexplorer.databinding.ItemRepositoryBinding
import com.abhishek.gitexplorer.viewmodel.HomeViewModel

class ItemAdapter(private val items: ObservableList<HomeViewModel.ItemViewModel>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class ItemViewHolder(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(viewModel: HomeViewModel.ItemViewModel) {
            binding.viewModel = viewModel
        }
    }
}