package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Order

@Dao
interface OrderDao : IDao<Order> {
    @Query("SELECT * FROM `Order` WHERE type = :type")
    fun getOrdersByType(type: String): List<Order?>

    @Query("SELECT * FROM `Order` WHERE id = :id")
    fun getOrder(id: Int): Order?
}