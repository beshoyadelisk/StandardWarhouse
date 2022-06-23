package com.gargour.warehouse.domain.use_case.item

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.repository.ItemRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class SearchItems @Inject constructor(private val repository: ItemRepository) {
    operator fun invoke(itemCode: String) = flow<Response<Any>> {
        emit(Response.Loading(View.VISIBLE))
        val result = repository.get(itemCode)
        result?.let {
            emit(Response.Success(result))
        } ?: emit(Response.Error("Item not found"))
    }.catch { emit(Response.Error(it.message ?: "Exception")) }
        .onCompletion { emit(Response.Loading(View.GONE)) }
}