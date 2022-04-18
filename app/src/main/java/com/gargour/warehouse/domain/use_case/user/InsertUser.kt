package com.gargour.warehouse.domain.use_case.user

import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.domain.repository.UserRepository

class InsertUser(private val repository: UserRepository) {
    suspend operator fun invoke(user: User){
        repository.insert(user)
    }

}