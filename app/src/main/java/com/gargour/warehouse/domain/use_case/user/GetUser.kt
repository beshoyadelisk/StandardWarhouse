package com.gargour.warehouse.domain.use_case.user

import android.view.View
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetUser(private val repository: UserRepository) {
    operator fun invoke(userName: String, password: String) = flow<Response<Any>> {
        try {
            emit(Response.Loading(View.VISIBLE))
            repository.getUser(userName).collect { user ->
                if (user != null) {
                    if (user.password != password)
                        emit(Response.Error("Wrong password!"))
                    else
                        emit(Response.Success(user))
                } else {
                    emit(Response.Error("User not found"))
                }
            }

        } catch (ex: Exception) {
            emit(Response.Error(ex.message ?: "Failed to get user data"))
        } finally {
            emit(Response.Loading(View.GONE))
        }

    }
}