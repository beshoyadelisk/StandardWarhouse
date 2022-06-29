package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao : IDao<Customer> {
    @Query("SELECT * FROM Customer")
    suspend fun getList(): List<Customer>

    @Query("SELECT * FROM Customer WHERE code = :code")
    fun get(code: String): Flow<Customer?>
}