package com.ramand.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.ramand.topmovies.databinding.ItemHomeMoviesLastBinding
import com.ramand.topmovies.models.home.ResponseMoviesList.Data
import javax.inject.Inject

class LastMoviesAdapter @Inject constructor() : RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {

    lateinit var binding: ItemHomeMoviesLastBinding
    private var moviesList = emptyList<Data>()

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun setData(item: Data){
            binding.apply {
                moviePosterImg.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                    diskCachePolicy(CachePolicy.ENABLED)
                }
                movieNameTxt.text = item.title
                movieRateTxt.text = item.imdbRating
                movieCountryTxt.text = item.country
                movieYearTxt.text = item.year
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
           }
        }
    }
    private var onItemClickListener:((Data) -> Unit)? = null
    fun setOnClickListener(listener: (Data) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    fun setData(data: List<Data>){
        val moviesDiffutils = MoviesDiffutils(moviesList,data)
        val diffutils = DiffUtil.calculateDiff(moviesDiffutils)
        moviesList = data
        diffutils.dispatchUpdatesTo(this)
    }

    class MoviesDiffutils(private val oldItem : List<Data>,private val newItem:List<Data>):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }

}

