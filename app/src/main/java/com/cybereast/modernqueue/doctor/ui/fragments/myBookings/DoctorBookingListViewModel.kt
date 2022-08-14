package com.cybereast.modernqueue.doctor.ui.fragments.myBookings

import androidx.lifecycle.ViewModel
import com.cybereast.modernqueue.models.Booking

class DoctorBookingListViewModel : ViewModel() {
    var sessionId: String? = null
    var bookingList: ArrayList<Booking> = ArrayList()
}