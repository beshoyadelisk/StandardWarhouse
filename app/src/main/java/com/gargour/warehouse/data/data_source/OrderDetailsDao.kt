package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gargour.warehouse.domain.model.OrderDetails

@Dao
interface OrderDetailsDao : IDao<OrderDetails> {
    @Query("Select * from OrderDetails where orderHeaderId = :id")
    suspend fun getOrderDetail(id: Int): List<OrderDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(data: OrderDetails): Long
}