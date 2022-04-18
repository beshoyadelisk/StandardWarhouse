package com.gargour.warehouse.domain.use_case.destination

import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.repository.CustomerRepository
import com.gargour.warehouse.domain.repository.SupplierRepository
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.flow

class DestinationUseCase(
    private val customerRepository: CustomerRepository,
    private val supplierRepository: SupplierRepository,
    private val warehouseRepository: WarehouseRepository
) {
    operator fun invoke(destination: Destination) = flow<Response<Destination>> {
        when(destination){
            is Destination.Customer -> {}
            is Destination.Supplier -> {}
            is Destination.Warehouse -> {}
        }
    }
}