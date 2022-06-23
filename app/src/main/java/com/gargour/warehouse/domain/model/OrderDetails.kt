package com.gargour.warehouse.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["orderHeaderId", "itemCode"])
data class OrderDetails(
    @ColumnInfo(name = "orderHeaderId") val orderHeaderId: Int,
    @ColumnInfo(name = "itemCode") val itemCode: String,
    val name: String,
    var qty: Int
)
