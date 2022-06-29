package com.gargour.warehouse.view.orders

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gargour.warehouse.R
import com.gargour.warehouse.databinding.DialogConfirmBinding
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
        viewModel.loadOrders()
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
        binding.ivNoData.visibility = if (ordersList.isEmpty()) {
            binding.tvNoData.text = getString(R.string.no_orders_found)
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.tvNoData.visibility = binding.ivNoData.visibility
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(orderHeader: OrderHeader, position: Int, view: View) {
        if (view.id == R.id.orderLayout) {
            findNavController().navigate(
                OrdersFragmentDirections.actionOrdersFragmentToScanFragment(
                    orderHeader
                )
            )
        } else if (view.id == R.id.ivDelete) {
            showDeleteConfirmDialog(orderHeader)
        }
    }

    private fun showDeleteConfirmDialog(orderHeader: OrderHeader) {
        try {
            val alertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogConfirmBinding = DialogConfirmBinding.inflate(li)
            alertDialog.setView(binding.root)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.tvHeader.text = getString(
                R.string.delete_data,
                getString(R.string.order).lowercase() + " " + orderHeader.id
            )
            binding.tvMessage.text =
                getString(R.string.delete_assurance_message, getString(R.string.order).lowercase())
            binding.btnDelete.setOnClickListener {
                viewModel.deleteOrder(orderHeader)
                alertDialog.dismiss()
            }
            binding.btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        } catch (ex: Exception) {
            showToast(getString(R.string.failed_to_delete_order))
            Log.d("showDeleteConfirmDialog", "Failed to delete item ${ex.message}")
        }
    }
}