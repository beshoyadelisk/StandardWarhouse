package com.gargour.warehouse.view.destination

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
import com.gargour.warehouse.databinding.DialogNewDestinationBinding
import com.gargour.warehouse.databinding.FragmentDestinationBinding
import com.gargour.warehouse.domain.model.IDestination
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.util.ViewExt.showToast
import com.gargour.warehouse.view.destination.adapter.DestinationAdapter
import com.gargour.warehouse.view.home.HomeSharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinationFragment : Fragment(), DestinationAdapter.DestinationListener {
    private var _binding: FragmentDestinationBinding? = null
    private val binding get() = _binding!!
    private val args: DestinationFragmentArgs by navArgs()
    private val viewModel: DestinationViewModel by viewModels()
    private lateinit var adapter: DestinationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDestinationBinding.inflate(inflater, container, false)
        binding.btnNewDestination.setOnClickListener { showNewDestinationDialog() }
        setTitle()
        initObservers()
        viewModel.loadDestinations()
        return binding.root
    }

    private fun showNewDestinationDialog() {
        val title = when (args.orderTypeArg) {
            OrderType.MainToWarehouseTransfer,
            OrderType.WarehouseToMainTransfer -> getString(R.string.new_warehouse)
            OrderType.Receive, OrderType.WarehouseToSupplierReturn -> getString(R.string.new_supplier)
            OrderType.Issue, OrderType.CustomerToWarehouseReturn -> getString(R.string.new_customer)
        }
        try {
            val alertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogNewDestinationBinding = DialogNewDestinationBinding.inflate(li)
            alertDialog.setView(binding.root)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.tvTitle.text = title
            binding.btnCreate.setOnClickListener {
                if (!binding.etCode.text.isNullOrEmpty() && !binding.etDescription.text.isNullOrEmpty()) {
                    val code = binding.etCode.text.toString()
                    val description = binding.etDescription.text.toString()
                    viewModel.addNewDestination(code, description)
                    alertDialog.dismiss()
                } else {
                    if (binding.etCode.text.isNullOrEmpty()) binding.etCode.error =
                        getString(R.string.missing)
                    if (binding.etDescription.text.isNullOrEmpty()) binding.etDescription.error =
                        getString(R.string.missing)
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

    private fun setTitle() {
        val title = when (args.orderTypeArg) {
            OrderType.MainToWarehouseTransfer,
            OrderType.WarehouseToMainTransfer -> getString(R.string.select_warehouse)
            OrderType.Receive, OrderType.WarehouseToSupplierReturn -> getString(R.string.select_supplier)
            OrderType.Issue, OrderType.CustomerToWarehouseReturn -> getString(R.string.select_customer)
        }
        binding.toolbarLayout.title = title
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.visibility = it }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.destinationResponse.observe(viewLifecycleOwner) { updateUi(it) }
        viewModel.actionResponse.observe(viewLifecycleOwner) { findNavController().navigate(it) }
    }

    private fun updateUi(destinationList: List<IDestination>) {
        adapter = DestinationAdapter(destinationList.toMutableList(), this)
        binding.rvDestination.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(destination: IDestination, position: Int) {
        viewModel.createOrder(destination)
    }
}