package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.OrderDao
import com.gargour.warehouse.domain.model.Order
import com.gargour.warehouse.domain.repository.OrderRepository

class OrderRepositoryImpl(private val dao: OrderDao) : OrderRepository {
    override suspend fun insert(data: Order) {
        dao.insert(data)
    }

    override suspend fun delete(data: Order) {
        dao.delete(data)
    }

    override suspend fun get(id: Int): Order? {
        return dao.getOrder(id)
    }

    override suspend fun getList(type: String): List<Order?> {
        return dao.getOrdersByType(type)
    }
}