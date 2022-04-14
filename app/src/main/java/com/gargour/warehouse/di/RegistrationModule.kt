package com.gargour.warehouse.di

import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.data.repository.RegistrationRepositoryImpl
import com.gargour.warehouse.domain.repository.RegistrationRepository
import com.gargour.warehouse.domain.use_case.registration.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegistrationModule {

    @Provides
    @Singleton
    fun providesRegistrationRepository(db: WarehouseDb): RegistrationRepository {
        return RegistrationRepositoryImpl(db.registrationDao)
    }

    @Provides
    @Singleton
    fun providesRegistrationUseCases(repository: RegistrationRepository): RegistrationUseCases {
        return RegistrationUseCases(
            insertRegistration = InsertRegistration(repository),
            getRegistration = GetRegistration(repository),
            removeRegistration = RemoveRegistration(repository),
            getSerial = GetSerial(),
            generateRegistration = GenerateRegistration()
        )
    }
}