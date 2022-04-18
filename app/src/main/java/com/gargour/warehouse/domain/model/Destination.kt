package com.gargour.warehouse.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class Destination:Parcelable {
    @Parcelize
    object CustomerDestination : Destination()
    @Parcelize
    object SupplierDestination : Destination()
    @Parcelize
    object WarehouseDestination : Destination()
}
