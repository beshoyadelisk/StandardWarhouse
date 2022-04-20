package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.OrderDao
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.domain.repository.OrderRepository

class OrderRepositoryImpl(private val dao: OrderDao) : OrderRepository {
    override suspend fun insert(data: OrderHeader): Long {
        return dao.insert(data)
    }

    override suspend fun delete(data: OrderHeader) {
        dao.delete(data)
    }

    override suspend fun get(id: Int): OrderHeader? {
        return dao.getOrder(id)
    }

    override suspend fun getList(type: String): List<OrderHeader?> {
        return dao.getOrdersByType(type)
    }
}