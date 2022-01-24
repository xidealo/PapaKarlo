package com.example.domain_firebase.model.firebase.cafe

data class CafeEntityFirebase(
    val fromTime: String = "",
    val toTime: String = "",
    val phone: String = "",
    val coordinate: CoordinateFirebase = CoordinateFirebase(),
    val visible: Boolean = false
)