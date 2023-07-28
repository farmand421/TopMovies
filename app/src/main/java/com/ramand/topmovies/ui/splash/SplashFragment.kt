package com.ramand.topmovies.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.ramand.topmovies.R
import com.ramand.topmovies.databinding.FragmentSplashBinding
import com.ramand.topmovies.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    //Binding
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var storeDataUser : StoreUserData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set Delay
        lifecycle.coroutineScope.launch {
            delay(2000)
        //Check User Token
            storeDataUser.getUserToken().collect(){
                if(it.isEmpty()){
                    findNavController().navigate(R.id.actionToRegister)
                }else {
                    findNavController().navigate(R.id.actionToHome)
                }

            }
        }
        findNavController().popBackStack(R.id.splashFragment,true)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}