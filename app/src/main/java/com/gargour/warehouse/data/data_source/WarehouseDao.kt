package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface WarehouseDao : IDao<Destination.Warehouse> {
    @Query("SELECT * FROM Warehouse")
     suspend fun getList(): List<Destination.Warehouse>
    @Query("SELECT * FROM Warehouse WHERE code = :code")
    fun get(code: String): Flow<Destination.Warehouse?>
}