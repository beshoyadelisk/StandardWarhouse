package com.gargour.warehouse.domain.use_case.registration

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetRegistration(private val repository: RegistrationRepository) {
    operator fun invoke(serial: String) = flow<Response<Any>> {
        try {
            emit(Response.Loading(View.VISIBLE))
            if (serial.isEmpty()) {
                emit(Response.Loading(View.GONE))
                emit(Response.Error("Failed to get serial"))
                return@flow
            }
            repository.getReg(serial).collect { registration ->
                if (registration == null) {
                    emit(Response.Error("Failed to get registration"))
                } else {
                    emit(Response.Success(registration))
                }
            }
        } catch (ex: Exception) {
            emit(Response.Error(ex.message ?: "Exception: Failed to get registration code"))
        } finally {
            emit(Response.Loading(View.GONE))
        }
    }
}