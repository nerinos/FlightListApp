package com.nerinos.flightlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlightInfo(
    val flight: Flight,
    val selectedType: FlightTypeEntity
) : Parcelable