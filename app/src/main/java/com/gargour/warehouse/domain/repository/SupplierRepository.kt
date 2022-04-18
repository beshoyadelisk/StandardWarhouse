package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {
    suspend fun insert(data: Supplier)
    suspend fun delete(data: Supplier)
    suspend fun get(code: String): Flow<Supplier?>
    suspend fun getList(): List<Supplier?>

}