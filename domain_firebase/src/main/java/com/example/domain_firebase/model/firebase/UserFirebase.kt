package com.example.domain_firebase.model.firebase

import com.example.domain_firebase.model.firebase.address.UserAddressFirebase
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase

data class UserFirebase(
    var phone: String = "",
    var email: String = "",
    var orders: HashMap<String, UserOrderFirebase> = hashMapOf(),
    var addresses: HashMap<String, UserAddressFirebase> = hashMapOf()
)