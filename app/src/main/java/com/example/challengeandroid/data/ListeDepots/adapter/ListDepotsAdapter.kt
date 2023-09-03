package com.example.challengeandroid.data.ListeDepots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroid.data.ListeDepots.model.ListReposDataModelItem
import com.example.challengeandroid.databinding.ReposItemBinding

interface IListDepotsAdapter

class ListDepotsAdapter :
    ListAdapter<ListReposDataModelItem, ListDepotsAdapter.ViewHolder>(BolusDiffUtils),
    IListDepotsAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReposItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repos = getItem(position)
        holder.bind(repos)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    inner class ViewHolder(private val binding: ReposItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repos: ListReposDataModelItem) {
            binding.Textviewtitle.text = repos.name
        }
    }

    object BolusDiffUtils : DiffUtil.ItemCallback<ListReposDataModelItem>() {
        override fun areItemsTheSame(
            oldItem: ListReposDataModelItem,
            newItem: ListReposDataModelItem,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ListReposDataModelItem,
            newItem: ListReposDataModelItem,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
