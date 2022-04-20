package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.OrderHeader

@Dao
interface OrderDao : IDao<OrderHeader> {
    //TODO Complete join query
//    @Query("SELECT Order_Header.* FROM Order_Header " +
//            "JOIN Supplier on Supplier.code = Order_Header.typeId " +
//            "JOIN Customer on Customer.code = Order_Header.typeId " +
//            "JOIN Warehouse on warehouse.code = Order_Header.typeId " +
//            "WHERE type = :type")
    @Query("SELECT * FROM Order_Header WHERE type = :type")
    fun getOrdersByType(type: String): List<OrderHeader?>

    @Query("SELECT * FROM Order_Header WHERE id = :id")
    fun getOrder(id: Int): OrderHeader?
}