package com.ramand.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramand.topmovies.databinding.ItemHomeGenreListBinding
import com.ramand.topmovies.databinding.ItemHomeMoviesTopBinding
import com.ramand.topmovies.models.home.ResponseGenresList
import com.ramand.topmovies.models.home.ResponseGenresList.ResponseGenresListItem
import com.ramand.topmovies.models.home.ResponseMoviesList.Data
import javax.inject.Inject

class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    lateinit var binding: ItemHomeGenreListBinding

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun setData(item: ResponseGenresListItem){
            binding.apply {
                nameTxt.text = item.name
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCollBack = object : DiffUtil.ItemCallback<ResponseGenresListItem>() {
        override fun areItemsTheSame(oldItem: ResponseGenresListItem, newItem: ResponseGenresListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseGenresListItem, newItem: ResponseGenresListItem): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCollBack)
}

