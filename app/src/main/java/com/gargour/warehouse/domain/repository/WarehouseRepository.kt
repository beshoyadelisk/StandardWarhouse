package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface WarehouseRepository {
    suspend fun insert(data: Destination.Warehouse)
    suspend fun delete(data: Destination.Warehouse)
    suspend fun get(code: String): Flow<Destination.Warehouse?>
    suspend fun getList(): List<Destination.Warehouse?>
}