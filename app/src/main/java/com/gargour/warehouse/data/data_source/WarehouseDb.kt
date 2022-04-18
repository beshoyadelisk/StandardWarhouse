package com.gargour.warehouse.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gargour.warehouse.domain.model.Destination
import com.gargour.warehouse.domain.model.Registration
import com.gargour.warehouse.domain.model.User


@Database(
    entities = [Registration::class, User::class, Destination.Customer::class,
        Destination.Supplier::class, Destination.Warehouse::class],
    version = 1
)
abstract class WarehouseDb : RoomDatabase() {
    abstract val registrationDao: RegistrationDao
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "Warehouse.db"
    }
}