package com.gargour.warehouse.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.databinding.FragmentHomeBinding
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scrollLayout.apply {
            receiveLinear.setOnClickListener { }
            issueLinear.setOnClickListener { }
            transferLinear.setOnClickListener { }
            returnsLinear.setOnClickListener { }
            settingsLinear.setOnClickListener { }
            logOutLinear.setOnClickListener { }
        }
    }

    private fun showError(message: String) {
        showToast(message)
    }

}
