package com.nerinos.flightlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChooseTypeEntity(
    val type: FlightTypeEntity,
    val price: Long,
    val currency: CurrencyEntity,
    var isChecked: Boolean = false
) : Parcelable
