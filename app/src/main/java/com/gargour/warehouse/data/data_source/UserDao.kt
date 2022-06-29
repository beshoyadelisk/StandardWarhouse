package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.gargour.warehouse.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : IDao<User> {
    @Query("SELECT * FROM USER WHERE username = :code")
      fun get(code: String): Flow<User?>

}