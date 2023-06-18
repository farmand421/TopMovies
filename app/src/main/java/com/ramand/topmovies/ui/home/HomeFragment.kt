package com.ramand.topmovies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ramand.topmovies.R
import com.ramand.topmovies.databinding.FragmentHomeBinding
import com.ramand.topmovies.ui.home.adapters.GenresAdapter
import com.ramand.topmovies.ui.home.adapters.LastMoviesAdapter
import com.ramand.topmovies.ui.home.adapters.TopMoviesAdapter
import com.ramand.topmovies.utils.initRecycler
import com.ramand.topmovies.utils.showInvisible
import com.ramand.topmovies.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    @Inject
    lateinit var lastMoviesAdapter: LastMoviesAdapter

    //Other
    private val viewModel: HomeViewModel by viewModels()
    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTOpMoviesList(3)
        viewModel.loadGenresList()
        viewModel.loadLastMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //GetTopMovies
            viewModel.topMoviesList.observe(viewLifecycleOwner) {
                topMoviesAdapter.differ.submitList(it.data)
                //RecyclerView
                topMoviesRecycler.initRecycler(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ), topMoviesAdapter
                )
                //Indicator
                pagerHelper.attachToRecyclerView(topMoviesRecycler)
                topMoviesIndicator.attachToRecyclerView(topMoviesRecycler, pagerHelper)
            }
            //GetGenres
            viewModel.genresList.observe(viewLifecycleOwner) {
                genresAdapter.differ.submitList(it)
                genresRecycler.initRecycler(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ), genresAdapter
                )
            }
            //GetLastMovies
            viewModel.lastMoviesList.observe(viewLifecycleOwner) {
                lastMoviesAdapter.setData(it.data)
                //RecyclerView
                lastMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    lastMoviesAdapter
                )
            }
            //Click
            lastMoviesAdapter.setOnClickListener {
                val directions = HomeFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(directions)
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    moviesLoading.showInvisible(true)
                    movieScrollLy.showInvisible(false)
                } else {
                    moviesLoading.showInvisible(false)
                    movieScrollLy.showInvisible(true)
                }
            }
        }
    }
}