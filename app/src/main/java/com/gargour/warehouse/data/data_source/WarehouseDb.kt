package com.gargour.warehouse.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gargour.warehouse.domain.model.*


@Database(
    entities = [Registration::class, User::class, Customer::class,
        Supplier::class, Warehouse::class],
    version = 1
)
abstract class WarehouseDb : RoomDatabase() {
    abstract val registrationDao: RegistrationDao
    abstract val userDao: UserDao
    abstract val customerDao: CustomerDao
    abstract val supplierDao: SupplierDao
    abstract val warehouseDao: WarehouseDao

    companion object {
        const val DATABASE_NAME = "Warehouse.db"
    }
}