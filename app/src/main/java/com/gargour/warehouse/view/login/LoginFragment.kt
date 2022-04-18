package com.gargour.warehouse.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.WarehouseApp
import com.gargour.warehouse.databinding.FragmentLoginBinding
import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.util.ViewExt.toGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { loading -> loadingObserver(loading) }
        viewModel.userData.observe(viewLifecycleOwner) { result -> userObserver(result) }
        viewModel.error.observe(viewLifecycleOwner) { error -> showError(error) }
    }

    private fun userObserver(result: User) {
        WarehouseApp.user = result
//        move to home fragment
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun loadingObserver(status: Int) {
        binding.progressBar.visibility = status
        binding.btnLogin.isEnabled = status != View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            binding.tvError.toGone()
            viewModel.login(binding.etUser.text.toString(), binding.etPass.text.toString())
        }
    }


    private fun showError(error: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = error

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}