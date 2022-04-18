package com.gargour.warehouse.domain.model

import androidx.room.Entity

@Entity
class Customer(code: String, name: String) : IDestination(code, name)
