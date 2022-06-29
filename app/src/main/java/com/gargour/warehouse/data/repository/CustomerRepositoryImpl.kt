package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.CustomerDao
import com.gargour.warehouse.domain.model.Customer
import com.gargour.warehouse.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class CustomerRepositoryImpl(private val dao: CustomerDao) : CustomerRepository {
    override suspend fun insert(data: Customer): Long {
        return dao.insert(data)
    }

    override suspend fun delete(data: Customer) {
        dao.delete(data)
    }

    override suspend fun get(code: String): Flow<Customer?> {
        return dao.get(code)
    }

    override suspend fun getList(): List<Customer?> {
        return dao.getList()
    }

}