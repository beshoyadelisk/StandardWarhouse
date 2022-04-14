package com.gargour.warehouse.domain.use_case.registration

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.InvalidRegistrationException
import com.gargour.warehouse.domain.model.Registration
import com.gargour.warehouse.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.flow

class InsertRegistration(
    private val repository: RegistrationRepository
) {
    @Throws(InvalidRegistrationException::class)
    suspend operator fun invoke(registration: Registration) = flow {
        try {
            emit(Response.Loading(View.VISIBLE))
            val result = repository.insertReg(registration)
            if (result > 0) {
                emit(Response.Success(result))
            } else {
                emit(Response.Error("Failed to insert registration"))
            }
        } catch (ex: Exception) {
            emit(Response.Error(ex.message ?: "Exception: Failed to insert registration"))
        } finally {
            emit(Response.Loading(View.GONE))
        }

    }


}