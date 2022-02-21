package com.nerinos.flightlist.utils

import com.nerinos.flightlist.entities.CurrencyEntity
import com.nerinos.flightlist.entities.FlightTypeEntity

fun CurrencyEntity.toCurrencyString(): String =
    when (this) {
        CurrencyEntity.RUB -> "₽"
        CurrencyEntity.USD -> "$"
        CurrencyEntity.EUR -> "€"
    }

fun FlightTypeEntity.toTypeString(): String =
    when (this) {
        FlightTypeEntity.BUSINESS -> "Бизнес-класс"
        FlightTypeEntity.ECONOMY -> "Эконом-класс"
    }

fun String.toAirportName(): String =
    when (this) {
        "SVO" -> "Moscow Sheremetyevo International Airport (Moscow)"
        "HND" -> "Haneda Airport (Tokyo)"
        "NRT" -> "Narita International Airport (Tokyo)"
        "EWR" -> "Newark Liberty International Airport (New York)"
        "DME" -> "Moscow Domodedovo Airport (Moscow)"
        "DOH" -> "Hamad International Airport (Doha)"
        "JFK" -> "John F Kennedy International Airport (New York)"
        "LHR" -> "London Heathrow Airport (London)"
        "FRA" -> "Frankfurt Am Main Airport (Frankfurt)"
        else -> this
    }