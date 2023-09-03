package com.example.challengeandroid.data.DetaisDepot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroid.data.DetaisDepot.model.DetaisDepotDataModel
import com.example.challengeandroid.databinding.DetaisDepotBinding

interface IDetaisDepotsAdapter
class DetailsDepotAdapter :
    ListAdapter<DetaisDepotDataModel, DetailsDepotAdapter.ViewHolder>(DetailsDepotDiffUtils),
    IDetaisDepotsAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DetaisDepotBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val depot = getItem(position)
        holder.bind(depot)
    }

    inner class ViewHolder(private val binding: DetaisDepotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(depot: DetaisDepotDataModel) {
            binding.textviewValueID.text = (depot.id ?: "").toString()
            binding.textviewNameValue.text = (depot.name ?: "").toString()
            binding.textviewfullNameValue.text = (depot.full_name ?: "").toString()
            binding.textviewUsernameValue.text = (depot.owner.login ?: "").toString()
            binding.textviewfullDesValue.text = (depot.description ?: "").toString()
            binding.textviewGitUrlValue.text = (depot.git_url ?: "").toString()
            binding.textviewSshUrlValue.text = (depot.ssh_url ?: "").toString()
            binding.textviewSvnUrlValue.text = (depot.svn_url ?: "").toString()
            binding.textviewlanguageValue.text = (depot.language ?: "").toString()
            binding.textviewVisibilityValue.text = (depot.visibility ?: "").toString()
            binding.textviewDefaultBrancheValue.text = (depot.default_branch ?: "").toString()
            binding.textviewSizeValue.text = (depot.size ?: "").toString()
            binding.textviewWatchersCountValue.text = (depot.watchers_count ?: "").toString()
            binding.textviewForksCountValue.text = (depot.forks_count ?: "").toString()
        }
    }

    object DetailsDepotDiffUtils : DiffUtil.ItemCallback<DetaisDepotDataModel>() {

        override fun areItemsTheSame(
            oldItem: DetaisDepotDataModel,
            newItem: DetaisDepotDataModel,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: DetaisDepotDataModel,
            newItem: DetaisDepotDataModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
