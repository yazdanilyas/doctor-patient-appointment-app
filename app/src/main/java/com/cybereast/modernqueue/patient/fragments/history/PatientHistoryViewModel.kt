package com.cybereast.modernqueue.patient.fragments.history

import androidx.lifecycle.ViewModel
import com.cybereast.modernqueue.models.Booking

class PatientHistoryViewModel : ViewModel() {
    var sessionId: String? = null
    var bookingList: ArrayList<Booking> = ArrayList()
}