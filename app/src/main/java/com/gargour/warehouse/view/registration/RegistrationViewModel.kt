package com.gargour.warehouse.view.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.Registration
import com.gargour.warehouse.domain.use_case.registration.RegistrationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases
) : ViewModel() {

    private val _deviceId = MutableLiveData<String?>()
    val deviceId: LiveData<String?> get() = _deviceId

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> get() = _loading

    private val _serialData = MutableLiveData<String?>()
    val serialData: LiveData<String?> get() = _serialData

    private val _actionResponse = MutableLiveData<NavDirections>()
    val actionResponse: LiveData<NavDirections> get() = _actionResponse

    init {
        _serialData.value = registrationUseCases.getSerial()
    }


    //RegistrationFragment
    fun registerApp(imeiString: String, serialString: String, userRegCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (imeiString.isNotEmpty() && serialString.isNotEmpty() && userRegCode.isNotEmpty()) {
                val orgCode: String? =
                    registrationUseCases.generateRegistration(imeiString, serialString)
                if (!orgCode.isNullOrEmpty() && userRegCode == orgCode) {
                    registrationUseCases.insertRegistration(
                        Registration(
                            serial = serialString,
                            imei = imeiString,
                            regCode = userRegCode
                        )
                    ).collect { response ->
                        when (response) {
                            is Response.Error -> {
                                _error.postValue(response.message!!)
                            }
                            is Response.Loading -> {
                                _loading.postValue(response.data as Int)
                            }
                            is Response.Success -> {
//                                _actionResponse.postValue(
//                                    RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment()
//                                )
                            }
                        }
                    }
                } else
                    _error.postValue("Invalid Code")
            } else {
                _error.postValue("Fill missing data")
            }
        }
    }


    fun setDeviceId(deviceId: String?) {
        _deviceId.value = deviceId
    }

}