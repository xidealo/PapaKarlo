package com.example.domain_firebase.model.firebase


data class OrderProductFirebase(
    var count: Int = 1,
    var menuProduct: MenuProductFirebase = MenuProductFirebase(),
)