package com.gargour.warehouse.view.splash

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.R
import com.gargour.warehouse.util.RequestPermissionHandler
import com.gargour.warehouse.util.RequestPermissionHandler.requestPermission
import com.gargour.warehouse.util.ViewExt.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(), RequestPermissionHandler.RequestPermissionListener {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requestPermission(
            requireActivity(),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            ),
            this
        )
        viewModel.actionResponse.observe(viewLifecycleOwner) { action ->
            Log.d("SplashFragment", "navigation start at: ${System.currentTimeMillis()} ")
            findNavController().navigate(action)
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onSuccess() {
        Log.d("RequestPermission", "requestPermission end at: ${System.currentTimeMillis()} ")
        viewModel.getRegistration()
    }

    override fun onFailed() {
        showToast("Failed to get permissions")
        requireActivity().finishAffinity()
    }

}