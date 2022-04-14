package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.gargour.warehouse.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = ABORT)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun removeUser(user: User)

    @Query("SELECT * FROM USER WHERE username = :username")
    fun getUser(username: String): Flow<User?>
}