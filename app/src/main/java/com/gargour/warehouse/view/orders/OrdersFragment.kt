package com.gargour.warehouse.view.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gargour.warehouse.databinding.FragmentOrdersBinding
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.util.ViewExt.showToast
import com.gargour.warehouse.view.orders.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(), OrdersAdapter.OrderListener {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val args: OrdersFragmentArgs by navArgs()
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var adapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        initObservers()
        viewModel.loadOrders(args.orderTypeArg)
        binding.btnNewOrder.setOnClickListener {
            val action =
                OrdersFragmentDirections.actionOrdersFragmentToDestinationFragment(args.orderTypeArg)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.visibility = it }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.ordersResponse.observe(viewLifecycleOwner) { updateUi(it) }
    }

    private fun updateUi(ordersList: List<OrderHeader>) {
        adapter = OrdersAdapter(ordersList.toMutableList(), this)
        binding.rvOrders.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(orderHeader: OrderHeader, position: Int) {
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToScanFragment(
                orderHeader
            )
        )
    }
}