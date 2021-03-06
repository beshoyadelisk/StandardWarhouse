package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.UserDao
import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override suspend fun insert(user: User) {
        dao.insert(user)
    }

    override suspend fun delete(user: User) {
        dao.delete(user)
    }

    override suspend fun get(username: String): Flow<User?> {
        return dao.get(username)
    }
}