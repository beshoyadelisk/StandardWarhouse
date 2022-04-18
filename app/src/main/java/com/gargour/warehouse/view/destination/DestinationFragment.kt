package com.gargour.warehouse.view.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gargour.warehouse.R
import com.gargour.warehouse.domain.model.IDestination
import com.gargour.warehouse.databinding.FragmentDestinationBinding
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.util.ViewExt.showToast
import com.gargour.warehouse.view.destination.adapter.DestinationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinationFragment : Fragment(), DestinationAdapter.DestinationListener {
    private var _binding: FragmentDestinationBinding? = null
    private val binding get() = _binding!!
    private val args: DestinationFragmentArgs by navArgs()
    private val viewModel: DestinationViewModel by viewModels()
    private lateinit var adapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.destination = args.destinationArg

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDestinationBinding.inflate(inflater, container, false)
        setTitle()
        initObservers()
        return binding.root
    }

    private fun setTitle() {
        val title = when (args.destinationArg) {
            is Destination.CustomerDestination -> getString(R.string.select_customer)
            is Destination.SupplierDestination -> getString(R.string.select_supplier)
            is Destination.WarehouseDestination -> getString(R.string.select_warehouse)
        }
        binding.toolbarLayout.title = title
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.visibility = it }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.destinationResponse.observe(viewLifecycleOwner) { updateUi(it) }
    }

    private fun updateUi(destinationList: List<IDestination>) {
        adapter = DestinationAdapter(destinationList.toMutableList(), this)
        binding.rvDestination.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDestination.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(destination: IDestination, position: Int) {
    }
}