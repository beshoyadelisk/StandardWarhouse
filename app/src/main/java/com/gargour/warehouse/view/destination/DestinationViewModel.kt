package com.gargour.warehouse.view.destination

import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.IDestination
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.domain.use_case.destination.GetDestinationUseCase
import com.gargour.warehouse.domain.use_case.destination.InsertDestinationUseCase
import com.gargour.warehouse.domain.use_case.order.header.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val getDestinationUseCase: GetDestinationUseCase,
    private val insertDestinationUseCase: InsertDestinationUseCase,
    private val orderUseCases: OrderUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderType: OrderType = savedStateHandle["orderTypeArg"]!!

    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _destinationResponse = MutableLiveData<List<IDestination>>()
    val destinationResponse: LiveData<List<IDestination>> = _destinationResponse

    private val _actionResponse = MutableLiveData<NavDirections>()
    val actionResponse: LiveData<NavDirections> get() = _actionResponse


    fun loadDestinations() {
        viewModelScope.launch(Dispatchers.IO) {
            getDestinationUseCase(orderType).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.data.toString())
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> _destinationResponse.postValue(response.data as List<IDestination>)
                }
            }
        }

    }

    fun createOrder(destination: IDestination) {
        viewModelScope.launch(Dispatchers.IO) {
            val date = DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date()).toString()
            val order =
                OrderHeader(
                    id = 0,
                    date = date,
                    typeId = destination.code,
                    destinationName = destination.name,
                    type = orderType.type
                )
            orderUseCases.createOrder(order).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.data.toString())
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> {
                        val id = response.data as Long
                        order.id = id.toInt()
                        _actionResponse.postValue(
                            DestinationFragmentDirections.actionDestinationFragmentToScanFragment(
                                order
                            )
                        )
                    }
                }
            }
        }
    }

    fun addNewDestination(code: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val destination = IDestination(code, description)
            insertDestinationUseCase(orderType, destination).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.data.toString())
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> loadDestinations()
                }
            }
        }
    }
}