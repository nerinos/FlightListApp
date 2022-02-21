package com.nerinos.flightlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flight(
    val currency: CurrencyEntity,
    val prices: List<Price>,
    val trips: List<Trip>
) : Parcelable