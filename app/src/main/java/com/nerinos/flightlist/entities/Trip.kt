package com.nerinos.flightlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trip(
    val from: String,
    val to: String
) : Parcelable