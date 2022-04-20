package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.OrderDetailsDao
import com.gargour.warehouse.domain.model.OrderDetails
import com.gargour.warehouse.domain.repository.OrderDetailsRepository

class OrderDetailsImpl(private val dao: OrderDetailsDao) : OrderDetailsRepository {
    override suspend fun insert(data: OrderDetails) {
        dao.insert(data)
    }

    override suspend fun delete(data: OrderDetails) {
        dao.delete(data)
    }
}