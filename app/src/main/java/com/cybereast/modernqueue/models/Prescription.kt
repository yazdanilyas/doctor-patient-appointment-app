package com.cybereast.modernqueue.models

import java.io.Serializable

data class Prescription(
    var sphLeftEye:String? = null,
    var sphRightEye: String? = null,
    var cylLeftEye: String? = null,
    var cylRightEye: String? = null,
    var axisLeftEye: String? = null,
    var axisRightEye:String? = null,
    var addLeftEye: String? = null,
    var addRightEye: String? = null,
    var prescription: String? = null,
    var currentDate: String? = null,
    val bookingId: String? = null
) : Serializable