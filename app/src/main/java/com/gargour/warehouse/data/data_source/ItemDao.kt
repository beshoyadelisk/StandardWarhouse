package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.Item

@Dao
interface ItemDao : IDao<Item>{
    @Query("SELECT * FROM Item WHERE code = :code")
    fun getItem(code: String): Item?
}