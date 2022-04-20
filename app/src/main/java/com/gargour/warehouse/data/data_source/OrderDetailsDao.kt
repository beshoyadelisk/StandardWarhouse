package com.gargour.warehouse.data.data_source

import androidx.room.Dao
import com.gargour.warehouse.domain.model.OrderDetails

@Dao
interface OrderDetailsDao : IDao<OrderDetails>