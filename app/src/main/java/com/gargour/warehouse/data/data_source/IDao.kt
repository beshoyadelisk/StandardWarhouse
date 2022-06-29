package com.gargour.warehouse.data.data_source

import androidx.room.*

interface IDao<T> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(data: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<T>?): LongArray?

    @Update
    suspend fun update(data: T): Int

    @Delete
    suspend fun delete(data: T): Int


}