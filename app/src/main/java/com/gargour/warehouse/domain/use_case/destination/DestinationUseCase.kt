package com.gargour.warehouse.domain.use_case.destination

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.repository.CustomerRepository
import com.gargour.warehouse.domain.repository.SupplierRepository
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class DestinationUseCase(
    private val customerRepository: CustomerRepository,
    private val supplierRepository: SupplierRepository,
    private val warehouseRepository: WarehouseRepository
) {
    operator fun invoke(destination: Destination) = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        when (destination) {
            is Destination.CustomerDestination -> {
                emit(Response.Success(customerRepository.getList()))
            }
            is Destination.SupplierDestination -> {
                emit(Response.Success(supplierRepository.getList()))
            }
            is Destination.WarehouseDestination -> {
                emit(Response.Success(warehouseRepository.getList()))
            }
        }
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}