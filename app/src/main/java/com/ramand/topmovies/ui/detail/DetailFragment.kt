package com.ramand.topmovies.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.canvas.CanvasCompat
import com.ramand.topmovies.R
import com.ramand.topmovies.databinding.FragmentDetailBinding
import com.ramand.topmovies.db.MovieEntity
import com.ramand.topmovies.utils.initRecycler
import com.ramand.topmovies.utils.showInvisible
import com.ramand.topmovies.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    //Binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    @Inject
    lateinit var entity: MovieEntity

    //Other
    private var movieId = 0
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //GetData
        movieId = args.movieid
        //CallApi
        if (movieId > 0) {
            viewModel.loadingDetailMovie(movieId)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InetViews
        binding.apply {
            //Loading Data
            viewModel.detailMovie.observe(viewLifecycleOwner) { response ->
                posterBigImg.load(response.poster)
                posterNormalImg.load(response.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.imdbRating
                movieTimeTxt.text = response.runtime
                movieDateTxt.text = response.released
                movieSummaryInfo.text = response.plot
                movieActorsInfo.text = response.actors
                //ImagesAdapter
                imagesAdapter.differ.submitList(response.images)
                imageRecycler.initRecycler(LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false), imagesAdapter)
                //Fav Click
                favImg.setOnClickListener {
                    entity.id = movieId
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.rated.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                    viewModel.favoriteMovie(movieId, entity)
                }
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailScrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }
            //Default Fav Icon Color
            lifecycleScope.launchWhenCreated {
                if (viewModel.existsMovie(movieId)) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),
                        R.color.philippineSilver))
                }
            }
            //Change Image With Click
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),
                        R.color.philippineSilver))
                }
            }
            //Back
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}