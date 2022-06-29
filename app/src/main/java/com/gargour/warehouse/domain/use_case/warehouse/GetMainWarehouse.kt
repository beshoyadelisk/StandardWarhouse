package com.gargour.warehouse.domain.use_case.warehouse

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class GetWarehouses @Inject constructor(private val repository: WarehouseRepository) {
    operator fun invoke() = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        val result = repository.getList()
        emit(Response.Success(result))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}

