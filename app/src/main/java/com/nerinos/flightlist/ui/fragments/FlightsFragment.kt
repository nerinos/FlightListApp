package com.nerinos.flightlist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nerinos.flightlist.R
import com.nerinos.flightlist.databinding.FlightsFragmentBinding
import com.nerinos.flightlist.ui.adapters.FlightsAdapter
import com.nerinos.flightlist.ui.viewmodels.FlightsViewModel
import com.nerinos.flightlist.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlightsFragment : Fragment(R.layout.flights_fragment) {
    private val viewModel: FlightsViewModel by activityViewModels()

    private var _binding: FlightsFragmentBinding? = null

    private val binding get() = _binding!!

    private val flightsAdapter: FlightsAdapter = FlightsAdapter { flight ->
        viewModel.onFlightsItemClicked(flight)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FlightsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFlights.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = flightsAdapter
        }

        viewModel.flightsFlow.observe(viewLifecycleOwner) { flightResponse ->
            binding.progressLoading.isVisible = flightResponse is Resource.Loading
            when (flightResponse) {
                is Resource.Success -> {
                    flightsAdapter.flights = flightResponse.data!!
                }
                is Resource.Error -> {
                    Snackbar.make(
                        binding.root,
                        flightResponse.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.showPricesEvent.observe(viewLifecycleOwner) { types ->
            findNavController().navigate(
                FlightsFragmentDirections.actionFlightsFragmentToTypeDialog(types.toTypedArray())
            )
        }

        viewModel.showFlightInfoEvent.observe(viewLifecycleOwner) { info ->
            findNavController().navigate(
                FlightsFragmentDirections.actionFlightsFragmentToFlightInfoFragment(info)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}