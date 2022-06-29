package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Warehouse
import kotlinx.coroutines.flow.Flow

@Dao
interface WarehouseDao : IDao<Warehouse> {
    @Query("SELECT * FROM Warehouse")
    suspend fun getList(): List<Warehouse>

    @Query("SELECT * FROM Warehouse WHERE code = :code")
    fun get(code: String): Flow<Warehouse?>

    @Query("SELECT * FROM Warehouse WHERE isMain = 1")
    fun getMainWarehouse(): Warehouse
}