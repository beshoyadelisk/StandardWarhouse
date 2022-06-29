package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun insert(data: Customer): Long
    suspend fun delete(data: Customer)
    suspend fun get(code: String): Flow<Customer?>
    suspend fun getList(): List<Customer?>
}