package com.gargour.warehouse.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.data.repository.CustomerRepositoryImpl
import com.gargour.warehouse.data.repository.SupplierRepositoryImpl
import com.gargour.warehouse.data.repository.UserRepositoryImpl
import com.gargour.warehouse.data.repository.WarehouseRepositoryImpl
import com.gargour.warehouse.domain.repository.CustomerRepository
import com.gargour.warehouse.domain.repository.SupplierRepository
import com.gargour.warehouse.domain.repository.UserRepository
import com.gargour.warehouse.domain.repository.WarehouseRepository
import com.gargour.warehouse.domain.use_case.destination.DestinationUseCase
import com.gargour.warehouse.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesEgoDatabase(app: Application): WarehouseDb {
        return Room.databaseBuilder(
            app,
            WarehouseDb::class.java,
            WarehouseDb.DATABASE_NAME
        ).addCallback(callBack)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    private val callBack = object : RoomDatabase.Callback() {
        private val USERNAME = "admin"
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("INSERT INTO USER VALUES ('$USERNAME','$USERNAME')")
        }
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Provides
    @Singleton
    fun providesCustomerRepository(db: WarehouseDb): CustomerRepository {
        return CustomerRepositoryImpl(db.customerDao)
    }

    @Provides
    @Singleton
    fun providesSupplierRepository(db: WarehouseDb): SupplierRepository {
        return SupplierRepositoryImpl(db.supplierDao)
    }

    @Provides
    @Singleton
    fun providesWarehouseRepository(db: WarehouseDb): WarehouseRepository {
        return WarehouseRepositoryImpl(db.warehouseDao)
    }

    @Provides
    @Singleton
    fun providesDestinationUseCases(
        customerRepository: CustomerRepository,
        supplierRepository: SupplierRepository,
        warehouseRepository: WarehouseRepository
    ): DestinationUseCase {
        return DestinationUseCase(
            customerRepository,
            supplierRepository,
            warehouseRepository
        )
    }


}