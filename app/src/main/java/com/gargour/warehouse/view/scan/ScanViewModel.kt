package com.gargour.warehouse.view.scan

import android.view.View
import androidx.lifecycle.*
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.Item
import com.gargour.warehouse.domain.model.OrderDetails
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.domain.use_case.item.LoadItems
import com.gargour.warehouse.domain.use_case.item.SearchItems
import com.gargour.warehouse.domain.use_case.order.details.GetOrderDetails
import com.gargour.warehouse.domain.use_case.order.details.InsertOrderDetails
import com.gargour.warehouse.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val loadItemsUseCase: LoadItems,
    private val searchItems: SearchItems,
    private val getOrderDetails: GetOrderDetails,
    private val insertOrderDetails: InsertOrderDetails,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderHeader: OrderHeader = savedStateHandle["OrderHeaderArg"]!!

    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val _currentItem = MutableLiveData<SingleEvent<Item>>()
    val searchedItem: LiveData<SingleEvent<Item>> get() = _currentItem

    private val _orderDetails = MutableLiveData<MutableList<OrderDetails>>()
    val orderDetails: LiveData<MutableList<OrderDetails>> get() = _orderDetails

    init {
        loadItems()
        loadOrderDetails()
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            loadItemsUseCase().collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.message!!)
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> _items.postValue(response.data as List<Item>)
                }
            }
        }
    }

    private fun loadOrderDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderDetails(orderHeader.id).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.message!!)
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> {
                        val list = response.data as MutableList<OrderDetails>
                        _orderDetails.postValue(ArrayList(list))
                    }
                }
            }
        }
    }

    fun searchItemsCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchItems(code).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.message!!)
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> _currentItem.postValue(SingleEvent(response.data as Item))
                }
            }
        }
    }

    fun addItemToOrderDetails(item: Item, qty: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val orderDetailsList = _orderDetails.value!!.toMutableList()
            val itemIndex = orderDetailsList.indexOfFirst { i -> i.itemCode == item.code }
            val orderDetails: OrderDetails = if (itemIndex != -1) {
                val oldItem = orderDetailsList.removeAt(itemIndex)
                val newItem = oldItem.copy()
                newItem.qty += qty
                orderDetailsList.add(itemIndex, newItem)
                newItem
            } else {
                OrderDetails(orderHeader.id, item.code, item.name, qty)
            }
            insertOrderDetails(orderDetails).collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.message!!)
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> loadOrderDetails()
                }
            }
        }
    }

}