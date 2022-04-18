package com.gargour.warehouse.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.use_case.registration.RegistrationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _actionResponse = MutableLiveData<NavDirections>()
    val actionResponse: LiveData<NavDirections> get() = _actionResponse

    fun getRegistration() {
        viewModelScope.launch(Dispatchers.IO) {
            val serial = registrationUseCases.getSerial()
            registrationUseCases.getRegistration(serial).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _error.postValue(response.message!!)
                        openRegisterFragment()
                    }
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        openLoginFragment()
                    }
                }
            }
        }
    }

    private fun openRegisterFragment() {
        _actionResponse.postValue(
            SplashFragmentDirections.actionSplashFragmentToRegistrationFragment()
        )
    }

    private fun openLoginFragment() {
        _actionResponse.postValue(
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        )
    }
}