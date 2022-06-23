package com.gargour.warehouse.domain.use_case.order.header

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.OrderType
import com.gargour.warehouse.domain.repository.OrderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class GetOrders(private val repository: OrderRepository) {
    operator fun invoke(orderType: OrderType) = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        val result = repository.getList(orderType.type)
        emit(Response.Success(result))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}