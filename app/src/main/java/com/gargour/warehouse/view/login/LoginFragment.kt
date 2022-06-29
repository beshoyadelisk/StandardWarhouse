package com.gargour.warehouse.view.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gargour.warehouse.WarehouseApp
import com.gargour.warehouse.databinding.FragmentLoginBinding
import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.util.Constants
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSharedPref()
        initObservers()
        binding.btnLogin.setOnClickListener {
            binding.tvError.toGone()
            viewModel.login(binding.etUser.text.toString(), binding.etPass.text.toString())
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { loading -> loadingObserver(loading) }
        viewModel.userData.observe(viewLifecycleOwner) { result -> userObserver(result) }
        viewModel.error.observe(viewLifecycleOwner) { error -> showError(error) }
    }

    private fun userObserver(result: User) {
        WarehouseApp.user = result
        saveUserToSharedPref(result)
//        move to home fragment
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun loadingObserver(status: Int) {
        binding.progressBar.visibility = status
        binding.btnLogin.isEnabled = status != View.VISIBLE
    }


    private fun saveUserToSharedPref(user: User) {
        val editor: SharedPreferences.Editor =
            requireContext().getSharedPreferences(
                Constants.WAREHOUSE_PREF_KEY,
                Context.MODE_PRIVATE
            )
                .edit()
        editor.putString(Constants.WAREHOUSE_PREF_KEY_USER, user.username)
        editor.putString(Constants.WAREHOUSE_PREF_KEY_PASS, user.password)
        editor.apply()
    }

    private fun getSharedPref() {
        Log.d("LoginFragment", "getSharedPref start at: ${System.currentTimeMillis()} ")
        val prefs: SharedPreferences =
            requireContext().getSharedPreferences(
                Constants.WAREHOUSE_PREF_KEY,
                Context.MODE_PRIVATE
            )
        val username = prefs.getString(Constants.WAREHOUSE_PREF_KEY_USER, null)
        val pass = prefs.getString(Constants.WAREHOUSE_PREF_KEY_PASS, null)
        if (username != null && pass != null) {
            val user = User(username, pass)
            WarehouseApp.user = user
            Log.d("LoginFragment", "getSharedPref end at: ${System.currentTimeMillis()} ")
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
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