package com.gargour.warehouse.domain.use_case.destination

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.domain.repository.CustomerRepository
import com.gargour.warehouse.domain.repository.SupplierRepository
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class GetDestinationUseCase(
    private val customerRepository: CustomerRepository,
    private val supplierRepository: SupplierRepository,
    private val warehouseRepository: WarehouseRepository
) {
    operator fun invoke(type: OrderType) = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        when (type) {
            OrderType.MainToWarehouseTransfer,
            OrderType.WarehouseToMainTransfer ->  emit(Response.Success(warehouseRepository.getList()))
            OrderType.Receive, OrderType.WarehouseToSupplierReturn -> emit(Response.Success(supplierRepository.getList()))
            OrderType.Issue, OrderType.CustomerToWarehouseReturn -> emit(Response.Success(customerRepository.getList()))
        }
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}