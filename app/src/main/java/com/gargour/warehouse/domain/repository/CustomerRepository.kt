package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Customer
import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun insert(data: Customer)
    suspend fun delete(data: Customer)
    suspend fun get(code: String): Flow<Customer?>
    suspend fun getList(): List<Customer?>
}