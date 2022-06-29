package com.gargour.warehouse.domain.use_case.destination

import android.util.Log
import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.*
import com.gargour.warehouse.domain.repository.CustomerRepository
import com.gargour.warehouse.domain.repository.SupplierRepository
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class InsertDestinationUseCase @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val supplierRepository: SupplierRepository,
    private val warehouseRepository: WarehouseRepository
) {
    operator fun invoke(type: OrderType, destination: IDestination) = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        val result = when (type) {
            OrderType.MainToWarehouseTransfer,
            OrderType.WarehouseToMainTransfer -> {
                val warehouse = Warehouse(destination.code, destination.name, false)
                warehouseRepository.insert(warehouse)
            }
            OrderType.Receive, OrderType.WarehouseToSupplierReturn -> {
                val supplier = Supplier(destination.code, destination.name)
                supplierRepository.insert(supplier)
            }
            OrderType.Issue, OrderType.CustomerToWarehouseReturn -> {
                val customer = Customer(destination.code, destination.name)
                customerRepository.insert(customer)
            }
        }
        Log.d("InsertDestination", "invoke: $result")
        if (result > 0)
            emit(Response.Success(result))
        else
            emit(Response.Error("Failed to insert"))
    }.catch {
        Log.d("InsertDestination", "InsertDestination: $it")
        emit(Response.Error(it.message ?: "Exception"))
    }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}