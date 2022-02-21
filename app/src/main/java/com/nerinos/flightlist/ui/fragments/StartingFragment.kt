package com.nerinos.flightlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nerinos.flightlist.R
import com.nerinos.flightlist.databinding.StartingFragmentBinding
import com.nerinos.flightlist.ui.viewmodels.StartingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartingFragment : Fragment(R.layout.starting_fragment) {

    private val viewModel: StartingViewModel by viewModels()

    private var _binding: StartingFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNavigate.setOnClickListener {
            viewModel.navigationButtonClicked()
        }
        viewModel.navigateEvent.observe(viewLifecycleOwner) {
            val action = StartingFragmentDirections.actionStartingFragmentToFlightsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}