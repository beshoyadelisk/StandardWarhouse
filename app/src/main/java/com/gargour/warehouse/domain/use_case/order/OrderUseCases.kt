package com.gargour.warehouse.domain.use_case.order

data class OrderUseCases(
    val getOrders: GetOrders,
    val createOrder: CreateOrder
)
