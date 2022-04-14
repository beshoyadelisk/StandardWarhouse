package com.gargour.warehouse.domain.use_case.user

data class UserUseCases(
    val insertUser: InsertUser,
    val getUser: GetUser,
    val removeUser: RemoveUser
)