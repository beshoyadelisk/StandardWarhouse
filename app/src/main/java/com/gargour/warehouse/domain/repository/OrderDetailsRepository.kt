package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.OrderDetails

interface OrderDetailsRepository {
    suspend fun insert(data: OrderDetails)
    suspend fun delete(data: OrderDetails): Int
    suspend fun getDetails(id: Int): List<OrderDetails>
}