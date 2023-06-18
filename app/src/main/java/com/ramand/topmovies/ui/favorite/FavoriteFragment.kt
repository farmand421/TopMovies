package com.ramand.topmovies.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramand.topmovies.databinding.FragmentFavoriteBinding
import com.ramand.topmovies.utils.initRecycler
import com.ramand.topmovies.utils.showInvisible
import com.ramand.topmovies.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //Binding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //ShowAllFavorite
            viewModel.loadingFavoriteList()
            //List
            viewModel.favoriteList.observe(viewLifecycleOwner) {
                favoriteAdapter.setData(it)
                favoriteRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    favoriteAdapter
                )
            }
            //Click
            favoriteAdapter.setOnClickListener {
                val directions = FavoriteFragmentDirections.actionToDetail(it.id)
                findNavController().navigate(directions)
            }
            //EmptyItems
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }
        }
    }
}