//
//  OrderDetailsViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 02.02.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI


struct OrderDetailsViewState {
    let orderUuid:String
    let orderProductItemList:[OrderProductItem]
    let deliveryCost: String?
    let newTotalCost:String
    let state: OrderDetailsDataState.ScreenState
    let code: String
    let orderInfo: OrderInfo?
    let discount:String?
}

struct OrderInfo{
    let status: OrderStatus
    let statusName: String
    let dateTime: String
    let deferredTime: String?
    let address: String
    let comment: String?
    let pickupMethod: String
    let deferredTimeHint: String
    let paymentMethod: LocalizedStringKey?
}

