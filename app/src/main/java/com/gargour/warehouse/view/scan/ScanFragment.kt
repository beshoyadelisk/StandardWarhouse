package com.gargour.warehouse.view.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gargour.warehouse.R
import com.gargour.warehouse.databinding.FragmentScanBinding
import com.gargour.warehouse.domain.model.OrderDetails
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()
    private val args: ScanFragmentArgs by navArgs()
    private val adapter = ScanAdapter { orderDetails -> adapterOnClick(orderDetails) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val order = args.OrderHeaderArg
        binding.header.tvOrderId.text = getString(R.string.orderNumb, order.id)
        binding.rvOrders.adapter = adapter
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.setHasFixedSize(true)
        binding.header.btnSearch.setOnClickListener {
            val code = binding.header.searchView.query.toString()
            if (code.isNotEmpty())
                viewModel.searchItemsCode(code)
            else
                showToast(getString(R.string.invalidCode))
        }
        initObservers()
        return binding.root
    }

    private fun adapterOnClick(orderDetails: OrderDetails) {
        showToast("Modify details $orderDetails")
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.visibility = it }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.orderDetails.observe(viewLifecycleOwner) { updateUi(it) }
        viewModel.searchedItem.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { viewModel.addItemToOrderDetails(it) }
        }
    }

    private fun updateUi(orderDetails: List<OrderDetails>) {
        adapter.submitList(orderDetails)
    }
}