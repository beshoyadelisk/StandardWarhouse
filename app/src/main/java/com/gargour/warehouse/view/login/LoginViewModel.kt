package com.gargour.warehouse.view.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.domain.use_case.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> get() = _loading

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    private fun loadUser(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCases.getUser(userName, password).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _error.postValue(response.message!!)
                        _loading.postValue(View.GONE)
                    }
                    is Response.Loading -> {
                        _loading.postValue(response.data as Int)
                    }
                    is Response.Success -> {
                        val user = response.data as User
                        _userData.postValue(user)
                        _loading.postValue(View.GONE)
                    }
                }
            }
        }
    }


    fun login(user: String, password: String) {
        if (user.isNotEmpty() && password.isNotEmpty()) {
            loadUser(user, password)
        } else {
            if (user.isEmpty()) _error.value = "Missing User"
            else if (password.isEmpty()) _error.value = "Missing password"
        }
    }
}