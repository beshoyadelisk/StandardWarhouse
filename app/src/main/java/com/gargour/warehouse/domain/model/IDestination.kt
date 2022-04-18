package com.gargour.warehouse.domain.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
open class IDestination(
    @PrimaryKey val code: String,
    val name: String
) : Parcelable