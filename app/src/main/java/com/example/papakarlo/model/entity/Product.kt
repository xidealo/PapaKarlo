package com.example.papakarlo.model.entity

data class Product(
    var id: String = "",
    var name: String = "",
    var cost: Long = 0,
    var gram: Int = 0,
    var description: String = "",
    var photoLink: String = ""
) {
}