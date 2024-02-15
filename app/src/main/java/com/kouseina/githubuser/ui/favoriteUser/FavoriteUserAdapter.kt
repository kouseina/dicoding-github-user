package com.kouseina.githubuser.ui.favoriteUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kouseina.githubuser.data.database.FavoriteUser
import com.kouseina.githubuser.databinding.ItemUserBinding

class FavoriteUserAdapter : ListAdapter<FavoriteUser, FavoriteUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUser){
            binding.tvName.text = user.username?: ""
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imageView)
            binding.linearLayout.setOnClickListener {
                val toDetailUserFragment = FavoriteUserFragmentDirections.actionFavoriteUserFragmentToDetailUserFragment(user.username ?: "")
                it.findNavController().navigate(toDetailUserFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}