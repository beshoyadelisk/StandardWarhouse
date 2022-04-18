package com.gargour.warehouse.view.registration

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.databinding.FragmentRegistrationBinding
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class RegistrationFragment : Fragment() {


    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()
    private lateinit var telephonyManager: TelephonyManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        telephonyManager =
            requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        initObservers()
        val deviceId = try {
            telephonyManager.deviceId
        } catch (ex: Exception) {
            Log.e("ExceptionDeviceId", "onCreateView: ", ex)
            null
        }
        viewModel.setDeviceId(deviceId)
        return binding.root
    }

    private fun initObservers() {
        viewModel.deviceId.observe(viewLifecycleOwner) { setDeviceIdText(it) }
        viewModel.serialData.observe(viewLifecycleOwner) { serialObserver(it) }
        viewModel.actionResponse.observe(viewLifecycleOwner) { resultResponse(it) }
        viewModel.error.observe(viewLifecycleOwner) { errorObserver(it) }
    }

    private fun errorObserver(message: String) {
        showToast(message)
    }

    private fun resultResponse(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun serialObserver(serial: String?) {
        if (serial != null) {
            binding.btnRegister.isEnabled = true
            binding.unlockEdit2.setText(serial)
        } else {
            binding.btnRegister.isEnabled = false
        }
    }

    @SuppressLint("HardwareIds")
    private fun setDeviceIdText(deviceId: String?) {
        binding.unlockEdit1.setText(deviceId)
        if (binding.unlockEdit1.text.toString().isEmpty()) {
            binding.unlockEdit1.setText(
                Settings.Secure.getString(
                    requireActivity().contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            viewModel.registerApp(
                binding.unlockEdit1.text.toString(),
                binding.unlockEdit2.text.toString(),
                binding.regEdit.text.toString()
            )
        }
        binding.btnCancel.setOnClickListener {
            requireActivity().finishAffinity()
        }
    }
}