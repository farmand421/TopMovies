package com.ramand.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramand.topmovies.databinding.ItemHomeMoviesTopBinding
import com.ramand.topmovies.models.home.ResponseMoviesList.Data
import javax.inject.Inject

class TopMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {
    lateinit var binding: ItemHomeMoviesTopBinding

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun setData(item: Data){
            binding.apply {
                moviePosterImg.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = item.title
                movieInfoTxt.text = "${item.imdbRating} | ${item.country} | ${item.year}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemHomeMoviesTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = if (differ.currentList.size > 5) 5 else differ.currentList.size

    private val differCollBack = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCollBack)
}

