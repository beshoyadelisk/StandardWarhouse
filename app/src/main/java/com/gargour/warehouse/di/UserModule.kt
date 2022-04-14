package com.gargour.warehouse.di

import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.data.repository.UserRepositoryImpl
import com.gargour.warehouse.domain.repository.UserRepository
import com.gargour.warehouse.domain.use_case.user.GetUser
import com.gargour.warehouse.domain.use_case.user.InsertUser
import com.gargour.warehouse.domain.use_case.user.RemoveUser
import com.gargour.warehouse.domain.use_case.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    @Singleton
    fun providesUserRepository(db: WarehouseDb): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun providesUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            insertUser = InsertUser(repository),
            getUser = GetUser(repository),
            removeUser = RemoveUser(repository)
        )
    }
}