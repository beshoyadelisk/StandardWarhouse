package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.SupplierDao
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.model.Supplier
import com.gargour.warehouse.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.Flow

class SupplierRepositoryImpl(private val dao: SupplierDao) : SupplierRepository {
    override suspend fun insert(data: Supplier) {
        dao.insert(data)
    }

    override suspend fun delete(data: Supplier) {
        dao.delete(data)
    }

    override suspend fun get(code: String): Flow<Supplier?> {
        return dao.get(code)
    }

    override suspend fun getList(): List<Supplier?> {
        return dao.getList()
    }
}