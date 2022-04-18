package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao : IDao<Destination.Customer> {
    @Query("SELECT * FROM Customer")
     suspend fun getList(): List<Destination.Customer>
    @Query("SELECT * FROM Customer WHERE code = :code")
    fun get(code: String): Flow<Destination.Customer?>
}