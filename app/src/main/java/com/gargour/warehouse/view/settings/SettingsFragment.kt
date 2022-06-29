package com.gargour.warehouse.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gargour.warehouse.R
import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.databinding.FragmentSettingsBinding
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val dbLocation = requireContext().getDatabasePath(WarehouseDb.DATABASE_NAME)
        initObservers()
        binding.exportLinear.setOnClickListener { viewModel.export(dbLocation) }
        binding.importLinear.setOnClickListener { viewModel.import(dbLocation) }
        return binding.root
    }

    private fun initObservers() {
        viewModel.importResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                handleImport(it)
            }
        }
        viewModel.exportResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                handleExport(it)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun handleImport(response: Boolean) {
        if (response) {
            showToast(getString(R.string.imported))
        } else {
            showToast(getString(R.string.failed_to_import))
        }
    }

    private fun handleExport(response: Boolean) {
        if (response) {
            showToast(getString(R.string.exported))
        } else {
            showToast(getString(R.string.failed_to_export))
        }
    }

}