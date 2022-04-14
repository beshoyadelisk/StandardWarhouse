package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.UserDao
import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override suspend fun insertUser(user: User) {
        dao.insertUser(user)
    }

    override suspend fun removeUser(user: User) {
        dao.removeUser(user)
    }

    override fun getUser(username: String): Flow<User?> {
        return dao.getUser(username)
    }
}