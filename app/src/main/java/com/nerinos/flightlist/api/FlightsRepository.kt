package com.nerinos.flightlist.api

import com.nerinos.flightlist.entities.Flight
import com.nerinos.flightlist.utils.Resource
import javax.inject.Inject

class FlightsRepository @Inject constructor(private val flightsApi: FlightsApi) {

    suspend fun getFlights(): Resource<List<Flight>> {
        val result = flightsApi.getFlights()
        return when {
            !result.isSuccessful -> Resource.Error(result.message())
            result.body() != null -> Resource.Success(result.body()!!)
            else -> throw RuntimeException("Unknown response")
        }
    }
}