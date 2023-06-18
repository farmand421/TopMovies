package com.ramand.topmovies.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramand.topmovies.databinding.ItemDetailImagesBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor() : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    lateinit var binding: ItemDetailImagesBinding

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun setData(item: String){
            binding.apply {
                itemImages.load(item){
                    crossfade(true)
                    crossfade(800)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemDetailImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCollBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCollBack)
}

