package com.gargour.warehouse.view.home

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.R
import com.gargour.warehouse.WarehouseApp
import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.databinding.DialogOrderTypeBinding
import com.gargour.warehouse.databinding.DialogWarehousesListBinding
import com.gargour.warehouse.databinding.FragmentHomeBinding
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.domain.model.Warehouse
import com.gargour.warehouse.util.ViewExt.showToast
import com.gargour.warehouse.view.home.adapter.WarehouseAdapter
import com.gargour.warehouse.view.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var warehouseAlertDialog: AlertDialog
    private val adapter = WarehouseAdapter { warehouse -> adapterOnClick(warehouse) }


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
        initObservers()
        getMainWarehouse()
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
            transferLinear.setOnClickListener {
                selectTypeDialog(
                    OrderType.MainToWarehouseTransfer,
                    OrderType.WarehouseToMainTransfer
                )
            }
            returnsLinear.setOnClickListener {
                selectTypeDialog(
                    OrderType.WarehouseToSupplierReturn,
                    OrderType.CustomerToWarehouseReturn
                )
            }
            settingsLinear.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment()) }
            binding.btnExportFab.setOnClickListener {
                val dbLocation = requireContext().getDatabasePath(WarehouseDb.DATABASE_NAME)
                viewModel.export(dbLocation)
            }
            logOutLinear.setOnClickListener { logout() }
        }
    }

    private fun initObservers() {
        viewModel.exportResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                handleExport(it)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }

        viewModel.warehousesResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { list ->
                if (list.isNotEmpty()) {
                    try {
                        val mainWarehouse = list.first { it.isMain }
                        WarehouseApp.mainWarehouse = mainWarehouse
                        HomeSharedPrefManager.saveMainWarehouseToSharedPref(
                            mainWarehouse,
                            requireContext()
                        )
                    } catch (ex: NoSuchElementException) {
                        showSelectMainWarehouseDialog(list)
                    }
                } else {
                    showToast(getString(R.string.please_load_data))
                }
            }
            changeUiVisibility(WarehouseApp.mainWarehouse != null)
        }
    }

    private fun showSelectMainWarehouseDialog(list: List<Warehouse>) {
        try {
            warehouseAlertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogWarehousesListBinding = DialogWarehousesListBinding.inflate(li)
            warehouseAlertDialog.setView(binding.root)
            warehouseAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            warehouseAlertDialog.setCancelable(false)
            binding.rvWarehouses.adapter = adapter
            binding.rvWarehouses.setHasFixedSize(true)
            adapter.submitList(list)
            warehouseAlertDialog.show()
        } catch (ex: Exception) {
            showToast(getString(R.string.failed_to_delete_order))
            Log.d("showDeleteConfirmDialog", "Failed to delete item ${ex.message}")
        }
    }

    private fun adapterOnClick(warehouse: Warehouse) {
        WarehouseApp.mainWarehouse = warehouse
        warehouseAlertDialog.dismiss()
        HomeSharedPrefManager.saveMainWarehouseToSharedPref(warehouse, requireContext())
        changeUiVisibility(WarehouseApp.mainWarehouse != null)
    }

    private fun changeUiVisibility(isVisible: Boolean) {
        binding.scrollLayout.apply {
            receiveCard.isVisible = isVisible
            issueCard.isVisible = isVisible
            returnsCard.isVisible = isVisible
            transferCard.isVisible = isVisible
        }

    }

    private fun handleExport(response: Boolean) {
        if (response) {
            showToast(getString(R.string.exported))
        } else {
            showToast(getString(R.string.failed_to_export))
        }
    }

    private fun selectTypeDialog(firstType: OrderType, secondType: OrderType) {
        try {
            val alertDialog = AlertDialog.Builder(context).create()
            val li = LayoutInflater.from(context)
            val binding: DialogOrderTypeBinding = DialogOrderTypeBinding.inflate(li)
            alertDialog.setView(binding.root)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.btnDest1.text = getString(firstType.descriptionId)
            binding.btnDest2.text = getString(secondType.descriptionId)
            binding.btnDest1.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToOrdersFragment(firstType)
                findNavController().navigate(action)
                alertDialog.dismiss()
            }
            binding.btnDest2.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToOrdersFragment(secondType)
                findNavController().navigate(action)
                alertDialog.dismiss()
            }
            alertDialog.show()
        } catch (ex: Exception) {
            Toast.makeText(context, "Dialog Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        WarehouseApp.user = null
        HomeSharedPrefManager.clearUserSharedPref(requireContext())
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    /**
     * Get main warehouse if saved a @WarehouseApp
     * Else try to get the mainWarehouse from sharedPref ($getMainWarehouseSharedPref)
     * Else load list of available warehouses (${viewModel.getWarehouses()}) and let user choose main warehouse then saved it to sharedPref ($saveMainWarehouseToSharedPref)
     * Else let user load data by file transfer
     */
    private fun getMainWarehouse() {
        if (WarehouseApp.mainWarehouse == null) {
            val savedWarehouse = HomeSharedPrefManager.getMainWarehouseSharedPref(requireContext())
            if (savedWarehouse != null) {
                WarehouseApp.mainWarehouse = savedWarehouse
            } else {
                viewModel.getWarehouses()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
