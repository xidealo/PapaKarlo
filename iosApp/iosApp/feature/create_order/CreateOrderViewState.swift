//
//  CreateOrderViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import Foundation
import SwiftUI

struct CreateOrderViewState {
    let createOrderType: CreateOrderType
    let isAddressErrorShown: Bool
    let deferredTime: String
    let deferredTimeStringId: LocalizedStringKey
    let selectedPaymentMethod: PaymentMethodUI?
    let isPaymentMethodErrorShown: Bool
    let showChange: Bool
    let withoutChange: LocalizedStringKey
    let changeFrom: LocalizedStringKey
    let withoutChangeChecked: Bool
    let change: String
    let isChangeErrorShown: Bool
    let comment: String

    let cartTotal: CartTotalUI
    let isLoadingCreateOrder: Bool
    let isDeferredTimeShown: Bool
    let timePicker: TimePickerUI
    let paymentMethodList: PaymentMethodListUI
    let isOrderCreationEnabled: Bool
    let isLoadingSwitcher: Bool

    var isFieldsEnabled: Bool {
        return !isLoadingCreateOrder
    }

    var switcherPosition: Int {
        if case .delivery = createOrderType {
            return 0
        } else {
            return 1
        }
    }
}

enum CreateOrderType {
    case pickup(Pickup)
    case delivery(Delivery)

    struct Pickup {
        let pickupAddress: String?
        let pickupAddressList: PickupAddressListUI
        let hasOpenedCafe: Bool
        let isEnabled: Bool
    }

    struct Delivery {
        let deliveryAddress: String?
        let deliveryAddressList: DeliveryAddressListUI
        let state: State
        let workload: Workload

        enum Workload: String, Equatable {
            case low = "LOW"
            case average = "AVERAGE"
            case high = "HIGH"
        }

        enum State: String, Equatable {
            case notEnabled = "NOT_ENABLED"
            case enabled = "ENABLED"
            case needAddress = "NEED_ADDRESS"
        }
    }
}

enum CartTotalUI {
    case loading
    case success(Success)

    struct Success {
        let motivation: MotivationUi?
        let discount: String?
        let deliveryCost: String?
        let oldFinalCost: String?
        let newFinalCost: String
    }
}

struct DeliveryAddressListUI {
    let isShown: Bool
    let addressList: [SelectableAddressUI]
}

struct PickupAddressListUI {
    let isShown: Bool
    let addressList: [SelectableAddressUI]
}

struct PaymentMethodListUI {
    let isShown: Bool
    let paymentMethodList: [SelectablePaymentMethodUI]
}

struct TimePickerUI: Equatable {
    let isShown: Bool
    let minTime: TimeUI
    let initialTime: TimeUI
}

struct TimeUI: Equatable {
    let hours: Int
    let minutes: Int
}

struct SelectableAddressUI {
    let uuid: String
    let address: String
    let isSelected: Bool
    let isEnabled: Bool
}

struct SelectablePaymentMethodUI: Identifiable {
    let id: String
    let name: LocalizedStringKey
    let isSelected: Bool
}
