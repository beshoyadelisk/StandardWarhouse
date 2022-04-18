package com.gargour.warehouse.domain.use_case.user

import com.gargour.warehouse.domain.model.User
import com.gargour.warehouse.domain.repository.UserRepository

class RemoveUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.delete(user)
    }
}