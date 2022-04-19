package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Order

interface OrderRepository {
    suspend fun insert(data: Order)
    suspend fun delete(data: Order)
    suspend fun get(id: Int): Order?
    suspend fun getList(type: String): List<Order?>
}