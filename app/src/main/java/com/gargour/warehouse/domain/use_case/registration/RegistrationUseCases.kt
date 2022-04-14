package com.gargour.warehouse.domain.use_case.registration

data class RegistrationUseCases(
    val insertRegistration: InsertRegistration,
    val removeRegistration: RemoveRegistration,
    val getRegistration: GetRegistration,
    val getSerial: GetSerial,
    val generateRegistration: GenerateRegistration
)
