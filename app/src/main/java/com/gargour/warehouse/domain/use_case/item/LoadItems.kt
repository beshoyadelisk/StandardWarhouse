package com.gargour.warehouse.domain.use_case.item

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.repository.ItemRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class LoadItems @Inject constructor(private val repository: ItemRepository) {
    operator fun invoke() = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        val result = repository.getList()
        emit(Response.Success(result))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}