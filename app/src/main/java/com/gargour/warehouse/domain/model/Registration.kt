package com.gargour.warehouse.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Registration")
data class Registration(
    @PrimaryKey
    val serial: String,
    val imei: String,
    val regCode: String
)

class InvalidRegistrationException(message: String) : Exception(message)