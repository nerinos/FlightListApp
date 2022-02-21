package com.nerinos.flightlist.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class CurrencyEntity(val value: String) : Parcelable {
    @SerializedName("RUB")
    RUB("RUB"),

    @SerializedName("USD")
    USD("USD"),

    @SerializedName("EUR")
    EUR("EUR")
}