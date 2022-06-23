package com.gargour.warehouse.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.data.repository.*
import com.gargour.warehouse.domain.repository.*
import com.gargour.warehouse.domain.use_case.destination.DestinationUseCase
import com.gargour.warehouse.domain.use_case.order.header.CreateOrder
import com.gargour.warehouse.domain.use_case.order.header.GetOrders
import com.gargour.warehouse.domain.use_case.order.header.OrderUseCases
import com.gargour.warehouse.domain.use_case.settings.SettingsUseCases
import com.gargour.warehouse.domain.use_case.settings.export.ExportDatabase
import com.gargour.warehouse.domain.use_case.settings.import.ImportDatabase
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
            db.execSQL("INSERT INTO Supplier VALUES ('123','supplier1')")
            db.execSQL("INSERT INTO Supplier VALUES ('342','supplier2')")
            db.execSQL("INSERT INTO Supplier VALUES ('3421','supplier3')")
            db.execSQL("INSERT INTO Supplier VALUES ('3422','supplier4')")
            db.execSQL("INSERT INTO Supplier VALUES ('3423','supplier5')")
            db.execSQL("INSERT INTO Supplier VALUES ('3424','supplier6')")
            db.execSQL("INSERT INTO Supplier VALUES ('3425','supplier7')")
            db.execSQL("INSERT INTO Supplier VALUES ('3426','supplier8')")
            db.execSQL("INSERT INTO Supplier VALUES ('3427','supplier9')")
            db.execSQL("INSERT INTO Supplier VALUES ('3428','supplier10')")
            db.execSQL("INSERT INTO Supplier VALUES ('3429','supplier11')")
            db.execSQL("INSERT INTO Supplier VALUES ('3420','supplier12')")
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
    fun provideOrderRepository(db: WarehouseDb): OrderRepository {
        return OrderRepositoryImpl(db.orderDao)
    }

    @Provides
    @Singleton
    fun provideOrderDetailsRepository(db: WarehouseDb): OrderDetailsRepository {
        return OrderDetailsImpl(db.orderDetailsDao)
    }

    @Provides
    @Singleton
    fun provideItemRepository(db: WarehouseDb): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
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

    @Provides
    @Singleton
    fun providesOrderUseCases(
        orderRepository: OrderRepository
    ): OrderUseCases {
        return OrderUseCases(
            getOrders = GetOrders(orderRepository),
            createOrder = CreateOrder(orderRepository)
        )
    }

    @Provides
    @Singleton
    fun providesSettingsUseCases(): SettingsUseCases {
        return SettingsUseCases(
            importDatabase = ImportDatabase(),
            exportDatabase = ExportDatabase()
        )
    }

}