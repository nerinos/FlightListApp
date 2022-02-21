package com.nerinos.flightlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    val type: FlightTypeEntity,
    val amount: Long,
    var isChecked: Boolean = false
) : Parcelable