package com.nerinos.flightlist.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.snplc.other.SingleLiveEvent
import com.example.snplc.other.toImmutable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor() : ViewModel() {

    private val _navigateEvent = SingleLiveEvent<Unit>()
    val navigateEvent = _navigateEvent.toImmutable()

    fun navigationButtonClicked() {
        _navigateEvent.postValue(Unit)
    }
}