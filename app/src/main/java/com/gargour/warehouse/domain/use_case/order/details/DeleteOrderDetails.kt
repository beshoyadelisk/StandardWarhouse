package com.gargour.warehouse.domain.use_case.order.details

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.OrderDetails
import com.gargour.warehouse.domain.repository.OrderDetailsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class DeleteOrderDetails @Inject constructor(private val repository: OrderDetailsRepository) {
    operator fun invoke(orderDetails: OrderDetails) = flow {
        emit(Response.Loading(View.VISIBLE))
        val result = repository.delete(orderDetails)
        if (result >= 1)
            emit(Response.Success(result))
        else
            emit(Response.Error("Failed to delete the selected item"))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}