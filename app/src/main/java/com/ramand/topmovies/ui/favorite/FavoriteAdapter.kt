package com.ramand.topmovies.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramand.topmovies.databinding.ItemHomeMoviesLastBinding
import com.ramand.topmovies.db.MovieEntity
import com.ramand.topmovies.models.home.ResponseMoviesList.Data
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    lateinit var binding: ItemHomeMoviesLastBinding
    private var moviesList = emptyList<MovieEntity>()

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun setData(item: MovieEntity){
            binding.apply {
                moviePosterImg.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = item.title
                movieRateTxt.text = item.rate
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
    private var onItemClickListener:((MovieEntity) -> Unit)? = null
    fun setOnClickListener(listener: (MovieEntity) -> Unit){
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

    fun setData(data: List<MovieEntity>){
        val moviesDiffutils = MoviesDiffutils(moviesList,data)
        val diffutils = DiffUtil.calculateDiff(moviesDiffutils)
        moviesList = data
        diffutils.dispatchUpdatesTo(this)
    }

    class MoviesDiffutils(private val oldItem : List<MovieEntity>,private val newItem:List<MovieEntity>):DiffUtil.Callback(){
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

