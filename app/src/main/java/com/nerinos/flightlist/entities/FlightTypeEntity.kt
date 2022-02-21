package com.nerinos.flightlist.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class FlightTypeEntity(val value: String) : Parcelable {
    @SerializedName("economy")
    ECONOMY("economy"),

    @SerializedName("bussiness")
    BUSINESS("bussiness"),
}