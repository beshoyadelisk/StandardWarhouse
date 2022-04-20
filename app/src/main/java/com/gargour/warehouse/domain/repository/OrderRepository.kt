package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.OrderHeader

interface OrderRepository {
    suspend fun insert(data: OrderHeader): Long
    suspend fun delete(data: OrderHeader)
    suspend fun get(id: Int): OrderHeader?
    suspend fun getList(type: String): List<OrderHeader?>
}