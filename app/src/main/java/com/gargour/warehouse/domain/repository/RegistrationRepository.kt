package com.gargour.warehouse.domain.repository

import com.gargour.warehouse.domain.model.Registration
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun insertReg(registration: Registration):Long
    suspend fun removeReg(registration: Registration)
    fun getReg(serial: String): Flow<Registration?>
}