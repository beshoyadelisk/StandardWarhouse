package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao : IDao<Destination.Supplier> {
    @Query("SELECT * FROM Supplier")
     suspend fun getList(): List<Destination.Supplier>
    @Query("SELECT * FROM Supplier WHERE code = :code")
    fun get(code: String): Flow<Destination.Supplier?>
}