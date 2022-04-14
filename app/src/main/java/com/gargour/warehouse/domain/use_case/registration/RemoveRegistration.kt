package com.gargour.warehouse.domain.use_case.registration

import com.gargour.warehouse.domain.model.Registration
import com.gargour.warehouse.domain.repository.RegistrationRepository

class RemoveRegistration(private val repository: RegistrationRepository) {
    suspend operator fun invoke(registration: Registration) {
        repository.removeReg(registration)
    }
}