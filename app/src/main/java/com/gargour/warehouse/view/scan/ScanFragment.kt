package com.gargour.warehouse.view.scan

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gargour.warehouse.R
import com.gargour.warehouse.databinding.DialogConfirmBinding
import com.gargour.warehouse.databinding.DialogEditItemBinding
import com.gargour.warehouse.databinding.FragmentScanBinding
import com.gargour.warehouse.domain.model.OrderDetails
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : ScannerFragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()
    private val args: ScanFragmentArgs by navArgs()
    private val adapter =
        ScanAdapter { orderDetails, view -> adapterOnClick(orderDetails, view) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val order = args.OrderHeaderArg
        binding.header.tvOrderId.text = getString(R.string.orderNumb, order.id)
        binding.footer.tvDestinationName.text = order.destinationName
        binding.footer.tvDestinationTitle.text = getDestinationTitle(order.type)
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

    override fun onScannerResultChange(result: String?) {
        if (result.isNullOrEmpty()) {
            showToast(getString(R.string.invalidCode))
        } else {
            binding.header.searchView.setQuery(result, false)
            viewModel.searchItemsCode(result)
        }
    }

    private fun getDestinationTitle(type: String): String {
        return when (type) {
            OrderType.MainToWarehouseTransfer.type,
            OrderType.WarehouseToMainTransfer.type -> getString(R.string.warehouse)
            OrderType.Receive.type, OrderType.WarehouseToSupplierReturn.type -> getString(R.string.supplier)
            OrderType.Issue.type, OrderType.CustomerToWarehouseReturn.type -> getString(R.string.customer)
            else -> ""
        }
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
//        adapter.submitList(null)
        adapter.submitList(ArrayList(orderDetails))
        binding.footer.apply {
            tvTotalItems.text = orderDetails.size.toString()
            tvTotalQuantity.text = orderDetails.sumOf { it.qty }.toString()
        }

    }

    private fun adapterOnClick(orderDetails: OrderDetails, view: View) {
        if (view.id == R.id.btnDelete) {
            showDeleteConfirmDialog(orderDetails)
        } else if (view.id == R.id.constraintLayout2) {
            showEditDialog(orderDetails)
        }
    }

    private fun showEditDialog(orderDetails: OrderDetails) {
        try {
            val alertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogEditItemBinding = DialogEditItemBinding.inflate(li)
            alertDialog.setView(binding.root)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.etQty.setText(orderDetails.qty.toString())
            binding.btnModify.setOnClickListener {
                if (!binding.etQty.text.isNullOrEmpty()) {
                    val qty: Int = binding.etQty.text.toString().toInt()
                    if (qty < orderDetails.qty) {
                        orderDetails.qty = qty
                        viewModel.updateOrderDetail(orderDetails)
                        alertDialog.dismiss()
                    } else {
                        binding.etQty.error = "Quantity should be less than ${orderDetails.qty}"
                    }
                } else {
                    if (binding.etQty.text.isNullOrEmpty()) binding.etQty.error =
                        "Missing"
                }
            }
            binding.btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        } catch (ex: Exception) {
            showToast("Failed to edit item")
            Log.d("showEditDialog", "Failed to edit item ${ex.message}")
        }
    }

    private fun showDeleteConfirmDialog(orderDetails: OrderDetails) {
        try {
            val alertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogConfirmBinding = DialogConfirmBinding.inflate(li)
            alertDialog.setView(binding.root)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.tvHeader.text = getString(
                R.string.delete_data,
                getString(R.string.item).lowercase() + " " + orderDetails.itemCode
            )
            binding.tvMessage.text =
                getString(R.string.delete_assurance_message, getString(R.string.item).lowercase())
            binding.btnDelete.setOnClickListener {
                viewModel.delete(orderDetails)
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