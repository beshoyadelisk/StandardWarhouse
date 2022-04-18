package com.gargour.warehouse.domain.model

import androidx.room.Entity

@Entity
class Warehouse(
    code: String,
    name: String,
    val isMain: Boolean
) : IDestination(code, name)
