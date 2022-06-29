package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao : IDao<Supplier> {
    @Query("SELECT * FROM Supplier")
     suspend fun getList(): List<Supplier>
    @Query("SELECT * FROM Supplier WHERE code = :code")
    fun get(code: String): Flow<Supplier?>
}