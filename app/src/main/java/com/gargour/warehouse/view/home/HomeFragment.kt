package com.gargour.warehouse.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.databinding.FragmentHomeBinding
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scrollLayout.apply {
            receiveLinear.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToOrdersFragment(OrderType.Receive)
                findNavController().navigate(action)
            }

            issueLinear.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToOrdersFragment(OrderType.Issue)
                findNavController().navigate(action)
            }
            transferLinear.setOnClickListener { }
            returnsLinear.setOnClickListener { }
            settingsLinear.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment()) }
            logOutLinear.setOnClickListener { }
        }
    }

    private fun showError(message: String) {
        showToast(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
