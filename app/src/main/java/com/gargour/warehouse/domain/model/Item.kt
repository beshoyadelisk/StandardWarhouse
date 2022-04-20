package com.gargour.warehouse.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(@PrimaryKey val code: String, val name: String)
