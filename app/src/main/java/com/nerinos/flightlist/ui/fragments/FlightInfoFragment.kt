package com.nerinos.flightlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nerinos.flightlist.R
import com.nerinos.flightlist.databinding.FlightInfoFragmentBinding
import com.nerinos.flightlist.ui.viewmodels.FlightInfoViewModel
import com.nerinos.flightlist.utils.slideUpViews
import com.nerinos.flightlist.utils.toAirportName
import com.nerinos.flightlist.utils.toCurrencyString
import com.nerinos.flightlist.utils.toTypeString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightInfoFragment : Fragment(R.layout.flight_info_fragment) {

    private val viewModel: FlightInfoViewModel by viewModels()

    private var _binding: FlightInfoFragmentBinding? = null

    private val args: FlightInfoFragmentArgs by navArgs()

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FlightInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flight = args.flightInfo.flight
        val price = flight.prices.find { priceItem ->
            priceItem.type == args.flightInfo.selectedType
        }

        binding.tvTripStartEnd.text = if (flight.trips.isNotEmpty()) {
            getString(
                R.string.from_to_template,
                flight.trips.first().from.toAirportName(),
                flight.trips.last().to.toAirportName()
            )
        } else {
            getString(
                R.string.unknown_destination
            )
        }
        binding.tvConnections.text = if (flight.trips.size == 1) {
            getString(
                R.string.connections_count_zero
            )
        } else {
            getString(
                R.string.connections_count,
                flight.trips.size - 1
            )
        }
        binding.tvPrice.text = getString(
            R.string.flight_cost_template,
            price?.amount,
            flight.currency.toCurrencyString()
        )

        binding.tvClass.text = args.flightInfo.selectedType.toTypeString()

        slideUpViews(
            requireContext(),
            binding.tvTripStartEnd,
            binding.tvPrice,
            binding.tvClass,
            binding.tvConnections,
            binding.layoutConnections
        )
        flight.trips.forEachIndexed { index, trip ->
            val tv = layoutInflater.inflate(R.layout.connection_item, null) as TextView
            tv.text = getString(
                R.string.from_to_template,
                trip.from.toAirportName(),
                trip.to.toAirportName()
            )
            binding.layoutConnections.addView(tv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}