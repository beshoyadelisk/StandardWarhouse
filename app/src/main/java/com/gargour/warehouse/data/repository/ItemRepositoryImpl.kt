package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.ItemDao
import com.gargour.warehouse.domain.model.Item
import com.gargour.warehouse.domain.repository.ItemRepository

class ItemRepositoryImpl(private val dao: ItemDao) : ItemRepository {
    override suspend fun insert(data: Item) {
        dao.insert(data)
    }

    override suspend fun delete(data: Item) {
        dao.delete(data)
    }

    override suspend fun get(code: String): Item? {
        return dao.getItem(code)
    }

    override suspend fun getList(): List<Item> {
        return dao.getItems()
    }
}