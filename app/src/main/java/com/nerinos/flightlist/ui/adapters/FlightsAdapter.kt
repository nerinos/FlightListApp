package com.nerinos.flightlist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nerinos.flightlist.R
import com.nerinos.flightlist.databinding.FlightItemBinding
import com.nerinos.flightlist.entities.Flight
import com.nerinos.flightlist.utils.toAirportName
import com.nerinos.flightlist.utils.toCurrencyString

class FlightsAdapter(
    val onFlightClickListener: ((Flight) -> Unit)
) : RecyclerView.Adapter<FlightsAdapter.FlightViewHolder>() {

    class FlightViewHolder(val binding: FlightItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Flight>() {
        override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
            return oldItem.trips == newItem.trips
        }

        override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, callback)

    var flights: List<Flight>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = FlightItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]
        holder.apply {
            val resources = itemView.resources
            val from = flight.trips.first().from
            val to = flight.trips.last().to
            if (flight.trips.isNotEmpty()) {
                binding.tvAirport.text = resources.getString(
                    R.string.from_to_template,
                    from.toAirportName(),
                    to.toAirportName()
                )
                binding.tvAirportCodes.text =
                    resources.getString(R.string.from_to_template, from, to)
            }

            binding.tvConnections.text = if (flight.trips.size == 1) {
                resources.getString(R.string.connections_count_zero)
            } else {
                resources.getString(R.string.connections_count, flight.trips.size - 1)
            }
            val price = flight.prices.minOf { price -> price.amount }
            binding.tvPrice.text = resources.getString(
                R.string.flight_cost_from_template,
                price,
                flight.currency.toCurrencyString()
            )
            itemView.setOnClickListener {
                onFlightClickListener(flight)
            }
        }
    }

    override fun getItemCount(): Int = flights.size

}