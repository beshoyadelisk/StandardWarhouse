package com.gargour.warehouse.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gargour.warehouse.domain.model.*
import com.gargour.warehouse.util.DateConverter


@Database(
    entities = [
        Registration::class,
        User::class,
        Customer::class,
        Supplier::class,
        Warehouse::class,
        OrderHeader::class,
        OrderDetails::class,
        Item::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class WarehouseDb : RoomDatabase() {
    abstract val registrationDao: RegistrationDao
    abstract val userDao: UserDao
    abstract val customerDao: CustomerDao
    abstract val supplierDao: SupplierDao
    abstract val warehouseDao: WarehouseDao
    abstract val orderDao: OrderDao
    abstract val orderDetailsDao: OrderDetailsDao
    abstract val itemDao: ItemDao

    companion object {
        const val DATABASE_NAME = "Warehouse.db"
    }
}