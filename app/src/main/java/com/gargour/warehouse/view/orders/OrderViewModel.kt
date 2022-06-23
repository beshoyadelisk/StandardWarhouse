package com.gargour.warehouse.view.orders

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.domain.use_case.order.header.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderUseCases: OrderUseCases) : ViewModel() {
    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var _ordersResponse = MutableLiveData<List<OrderHeader>>()
    val ordersResponse: LiveData<List<OrderHeader>> = _ordersResponse

    fun loadOrders(orderType: OrderType) {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCases.getOrders(orderType).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.data.toString())
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> _ordersResponse.postValue(response.data as List<OrderHeader>)
                }
            }
        }
    }



}