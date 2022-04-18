package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {
    suspend fun insert(data: Destination.Supplier)
    suspend fun delete(data: Destination.Supplier)
    suspend fun get(code: String): Flow<Destination.Supplier?>
    suspend fun getList(): List<Destination.Supplier?>

}