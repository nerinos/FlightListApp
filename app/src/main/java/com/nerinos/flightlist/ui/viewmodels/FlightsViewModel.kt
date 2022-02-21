package com.nerinos.flightlist.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerinos.flightlist.repository.FlightsRepository
import com.nerinos.flightlist.entities.ChooseTypeEntity
import com.nerinos.flightlist.entities.Flight
import com.nerinos.flightlist.entities.FlightInfo
import com.nerinos.flightlist.entities.FlightTypeEntity
import com.nerinos.flightlist.utils.Resource
import com.nerinos.flightlist.utils.SingleLiveEvent
import com.nerinos.flightlist.utils.toImmutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val repository: FlightsRepository
) : ViewModel() {
    private val _flightsFlow = MutableLiveData<Resource<List<Flight>>>()
    val flightsFlow = _flightsFlow.toImmutable()

    private val _showPricesEvent = SingleLiveEvent<List<ChooseTypeEntity>>()
    val showPricesEvent = _showPricesEvent.toImmutable()

    private val _showFlightInfoEvent = SingleLiveEvent<FlightInfo>()
    val showFlightInfoEvent = _showFlightInfoEvent.toImmutable()

    private var lastClickedFlight: Flight? = null
    private var lastClickedType: FlightTypeEntity? = null

    init {
        viewModelScope.launch {
            getFlights()
        }
    }

    suspend fun getFlights() = withContext(Dispatchers.IO) {
        _flightsFlow.postValue(Resource.Loading())
        val result = repository.getFlights()
        _flightsFlow.postValue(result)
    }

    fun onFlightsItemClicked(flight: Flight) {
        lastClickedFlight = flight
        val typeEntities = flight.prices.map { price ->
            ChooseTypeEntity(
                type = price.type,
                price = price.amount,
                currency = flight.currency
            )
        }
        _showPricesEvent.postValue(typeEntities)
    }

    fun onFlightTypeSelected(type: FlightTypeEntity) {
        lastClickedType = type
    }

    fun onFlightTypeConfirmed() {
        lastClickedFlight?.let { flight ->
            lastClickedType?.let { type ->
                _showFlightInfoEvent.postValue(
                    FlightInfo(
                        flight,
                        type
                    )
                )

            }
        }
        lastClickedFlight = null
        lastClickedType = null
    }
}