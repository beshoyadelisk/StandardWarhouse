package com.gargour.warehouse.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Date,
    val typeId: String,
    val type: OrderType
) : Parcelable


enum class OrderType(val type: String) {
    MainToWarehouseTransfer("MTW"),
    WarehouseToMainTransfer("WTM"),
    Receive("Receive"),
    Issue("Issue"),
    CustomerToWarehouseReturn("CTW"),
    WarehouseToSupplierReturn("WTS")
}