package com.gargour.warehouse.data.repository

import com.gargour.warehouse.data.data_source.RegistrationDao
import com.gargour.warehouse.domain.model.Registration
import com.gargour.warehouse.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow

class RegistrationRepositoryImpl(private val dao: RegistrationDao) : RegistrationRepository {
    override suspend fun insertReg(registration: Registration): Long {
        return dao.insert(registration)
    }

    override suspend fun removeReg(registration: Registration) {
        dao.delete(registration)
    }

    override fun getReg(serial: String): Flow<Registration> {
        return dao.getReg(serial)
    }
}