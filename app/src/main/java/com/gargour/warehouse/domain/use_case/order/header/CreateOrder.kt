package com.gargour.warehouse.domain.use_case.order.header

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.OrderHeader
import com.gargour.warehouse.domain.repository.OrderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class CreateOrder(private val orderRepository: OrderRepository) {
    operator fun invoke(orderHeader: OrderHeader) = flow {
        emit(Response.Loading(View.VISIBLE))
        emit(Response.Success(orderRepository.insert(orderHeader)))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}