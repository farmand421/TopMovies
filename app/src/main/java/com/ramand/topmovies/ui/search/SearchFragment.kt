package com.ramand.topmovies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramand.topmovies.R
import com.ramand.topmovies.databinding.FragmentSearchBinding
import com.ramand.topmovies.ui.home.HomeFragmentDirections
import com.ramand.topmovies.ui.home.adapters.LastMoviesAdapter
import com.ramand.topmovies.utils.initRecycler
import com.ramand.topmovies.utils.showInvisible
import com.ramand.topmovies.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    //Binding
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchAdapter: LastMoviesAdapter

    //Other
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Search
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()){
                    viewModel.loadingSearchMovies(search)
            }
            }
            //GetMoviesList
            viewModel.moviesList.observe(viewLifecycleOwner){
                searchAdapter.setData(it.data)
                moviesRecycler.initRecycler(LinearLayoutManager(requireContext()),searchAdapter)
            }
            //Click
            searchAdapter.setOnClickListener {
                val directions = SearchFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(directions)
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    searchLoading.showInvisible(true)
                }else{
                    searchLoading.showInvisible(false)
                }
            }
            //EmptyItems
            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    emptyItemsLay.showInvisible(true)
                    moviesRecycler.showInvisible(false)
                }else{
                    emptyItemsLay.showInvisible(false)
                    moviesRecycler.showInvisible(true)
                }
            }
        }
    }
}