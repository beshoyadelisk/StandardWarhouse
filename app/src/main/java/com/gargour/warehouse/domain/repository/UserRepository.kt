package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun removeUser(user: User)
    fun getUser(username: String): Flow<User?>
}