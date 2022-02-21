package com.nerinos.flightlist.api

import com.nerinos.flightlist.entities.FlightsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FlightsApi {
    companion object {
        const val BASE_URL = "https://603e34c648171b0017b2ec55.mockapi.io/"
    }

    @GET("ott/search")
    suspend fun getFlights(): Response<FlightsResponse>
}