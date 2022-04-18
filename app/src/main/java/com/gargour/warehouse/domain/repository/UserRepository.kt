package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insert(user: User)
    suspend fun delete(user: User)
    suspend fun get(username: String): Flow<User?>
}