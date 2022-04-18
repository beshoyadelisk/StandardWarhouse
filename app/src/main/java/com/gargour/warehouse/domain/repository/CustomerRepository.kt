package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun insert(data: Destination.Customer)
    suspend fun delete(data: Destination.Customer)
    suspend fun get(code: String): Flow<Destination.Customer?>
    suspend fun getList(): List<Destination.Customer?>
}