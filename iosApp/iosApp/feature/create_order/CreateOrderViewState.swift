//
//  OrderCreationUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import Foundation
import SwiftUI

struct CreateOrderViewState {
    var workType: WorkType
    var deliveryAddress:String?
    var pickupAddress: String?
    var isAddressErrorShown: Bool
    var deferredTime: String
    var deferredTimeStringLocolized: LocalizedStringKey
    var selectedPaymentMethod: PaymentMethodUI?
    var isPaymentMethodErrorShown: Bool
    var comment: String
    var cartTotal: CartTotalUI
    var isLoading: Bool
    var deliveryAddressList: DeliveryAddressListUI
    var pickupAddressList: PickupAddressListUI
    var isDeferredTimeShown: Bool
    var timePicker: TimePickerUI
    var paymentMethodList: PaymentMethodListUI
    var showChange: Bool
    var withoutChange: LocalizedStringKey
    var changeFrom: LocalizedStringKey
    var withoutChangeChecked: Bool
    var change: String
    var isChangeErrorShown: Bool
    var isOrderCreationEnabled: Bool
    
    func switchPosition(isDelivery:Bool) -> Int {
        if (isDelivery) {
            return 0
        } else {
            return 1
        }
    }
}

enum CartTotalUI {
    case Loading
    case Success(MotivationUi?, String?, String?, String?, String)
}

enum WorkType {
    case Pickup
    case Delivery
    case DeliveryAndPickup(Bool)
}

struct DeliveryAddressListUI {
    var isShown: Bool
    var addressList: [SelectableAddressUI]
}

struct PickupAddressListUI {
    var isShown: Bool
    var addressList: [SelectableAddressUI]
}

struct PaymentMethodListUI {
    var isShown: Bool
    var paymentMethodList: [SelectablePaymentMethodUI]
}

struct SelectableAddressUI {
    var uuid: String
    var address: String
    var isSelected: Bool
}

struct SelectablePaymentMethodUI : Identifiable {
    var id: String
    var name: LocalizedStringKey
    var isSelected: Bool
}

struct TimePickerUI {
    var isShown: Bool
    var minTime: TimeUI
    var initialTime: TimeUI
}

struct TimeUI {
    var hours: Int
    var minutes: Int
}
