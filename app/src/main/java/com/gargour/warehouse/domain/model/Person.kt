package com.gargour.warehouse.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class Destination(
    @PrimaryKey val code: String,
    val name: String
) {
    @Entity
    class Customer(code: String, name: String) : Destination(code, name)

    @Entity
    class Supplier(code: String, name: String) : Destination(code, name)

    @Entity
    class Warehouse(
        code: String,
        name: String,
        val isMain: Boolean
    ) : Destination(code, name)
}
