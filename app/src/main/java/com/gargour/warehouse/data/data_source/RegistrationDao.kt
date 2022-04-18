package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.gargour.warehouse.domain.model.Registration
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDao : IDao<Registration> {
    @Query("SELECT * FROM registration WHERE serial = :serial")
    fun getReg(serial: String): Flow<Registration>
}