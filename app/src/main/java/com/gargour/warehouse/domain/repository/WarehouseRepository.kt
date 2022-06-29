package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Warehouse
import kotlinx.coroutines.flow.Flow

interface WarehouseRepository {
    suspend fun insert(data: Warehouse): Long
    suspend fun delete(data: Warehouse)
    suspend fun getMainWarehouse(): Warehouse
    suspend fun get(code: String): Flow<Warehouse?>
    suspend fun getList(): List<Warehouse?>
}