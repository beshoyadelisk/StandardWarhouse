package com.gargour.warehouse.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gargour.warehouse.R
import com.gargour.warehouse.WarehouseApp
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Order_Header")
@Parcelize
data class OrderHeader(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val date: String,
    val type: String,
    val currentWarehouse: String = WarehouseApp.mainWarehouse!!.code,
    val typeId: String,
    val destinationName: String,
) : Parcelable


enum class OrderType(val type: String, val descriptionId: Int) {
    MainToWarehouseTransfer("MTW", R.string.main_to_warehouse_transfer),
    WarehouseToMainTransfer("WTM", R.string.warehouse_to_main_transfer),
    Receive("Receive", R.string.receive),
    Issue("Issue", R.string.issue),
    CustomerToWarehouseReturn("CTW", R.string.customer_to_warehouse_return),
    WarehouseToSupplierReturn("WTS", R.string.warehouse_to_supplier_return);

    override fun toString(): String {
        return type
    }
}