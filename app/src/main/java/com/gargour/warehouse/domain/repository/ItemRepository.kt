package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Item

interface ItemRepository {
    suspend fun insert(data: Item)
    suspend fun delete(data: Item)
    suspend fun get(code: String): Item?
}