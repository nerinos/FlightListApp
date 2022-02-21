package com.nerinos.flightlist.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nerinos.flightlist.R
import com.nerinos.flightlist.ui.adapters.PriceAdapter
import com.nerinos.flightlist.ui.viewmodels.FlightsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightTypeDialog : DialogFragment() {

    private val args: FlightTypeDialogArgs by navArgs()

    private val viewModel: FlightsViewModel by activityViewModels()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val priceAdapter = PriceAdapter(
            onTypeSelected = { type ->
                viewModel.onFlightTypeSelected(type)
            }
        )
        priceAdapter.types = args.typeEntities.toList()

        val rvPrices = RecyclerView(requireContext()).apply {
            adapter = priceAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.flight_type_dialog_choose_title))
            .setView(rvPrices)
            .setPositiveButton(getString(R.string.flight_type_dialog_choose_confirm)) { _, _ ->
                findNavController().popBackStack()
                viewModel.onFlightTypeConfirmed()
            }
            .create()
    }

}