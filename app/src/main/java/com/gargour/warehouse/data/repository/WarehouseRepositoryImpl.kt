package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.WarehouseDao
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.model.Warehouse
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.Flow

class WarehouseRepositoryImpl (private val dao: WarehouseDao) : WarehouseRepository {
    override suspend fun insert(data: Warehouse) {
        dao.insert(data)
    }

    override suspend fun delete(data: Warehouse) {
        dao.delete(data)
    }

    override suspend fun get(code: String): Flow<Warehouse?> {
        return dao.get(code)
    }

    override suspend fun getList(): List<Warehouse?> {
        return dao.getList()
    }
}