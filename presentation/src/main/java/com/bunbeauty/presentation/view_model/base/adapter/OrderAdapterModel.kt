package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.model.local.BaseModel

data class OrderAdapterModel(
    override var uuid: String,
    val orderStatus: String,
    val orderColor: Int,
    val code: String,
    val time: String,
    var deferredTime: String
) : BaseModel
