package com.gargour.warehouse.view.destination

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.IDestination
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.use_case.destination.DestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val destinationUseCase: DestinationUseCase,
) :
    ViewModel() {
    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _destinationResponse = MutableLiveData<List<IDestination>>()
    val destinationResponse: LiveData<List<IDestination>> = _destinationResponse

    var destination: Destination? = null

    fun loadDestinations() {
        viewModelScope.launch(Dispatchers.IO) {
            destination?.let {
                destinationUseCase.invoke(it).collect { response ->
                    when (response) {
                        is Response.Error -> _error.postValue(response.data.toString())
                        is Response.Loading -> _loading.postValue(response.data as Int)
                        is Response.Success -> _destinationResponse.postValue(response.data as List<IDestination>)
                    }
                }
            }
        }

    }
}