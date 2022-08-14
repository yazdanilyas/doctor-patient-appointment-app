package com.cybereast.modernqueue.models

class Session(
    var sessionId: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val noOfTokens: Int? = null,
    val address: String? = null,
    val booking: Boolean? = false
)