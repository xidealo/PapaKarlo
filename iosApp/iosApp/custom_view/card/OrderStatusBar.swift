//
//  OrderStatusBar.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.03.2022.
//

import shared
import SwiftUI

struct OrderStatusBar: View {
    var orderStatus: OrderStatus
    var orderStatusName: String
    var currentStep = 0

    init(orderStatus: OrderStatus, orderStatusName: String) {
        self.orderStatus = orderStatus
        self.orderStatusName = orderStatusName
        currentStep = getCurrentStep(orderStatus: orderStatus)
    }

    var body: some View {
        HStack(spacing: 0) {
            ForEach(0 ... 4, id: \.self) { i in
                Step(
                    index: i,
                    currentStep: getCurrentStep(orderStatus: orderStatus),
                    orderStatus: orderStatus
                )
                .padding(.horizontal, Diems.HALF_SMALL_PADDING)
            }.padding(.vertical, Diems.SMALL_PADDING)
        }
        .frame(maxWidth: .infinity)
        .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }

    func getCurrentStep(orderStatus: OrderStatus) -> Int {
        switch orderStatus {
        case OrderStatus.notAccepted: return 0
        case OrderStatus.accepted: return 1
        case OrderStatus.preparing: return 2
        case OrderStatus.sentOut: return 3
        case OrderStatus.done: return 3
        case OrderStatus.delivered: return 4
        case OrderStatus.canceled: return 0
        default: return 0
        }
    }
}

struct Step: View {
    let index: Int
    let currentStep: Int
    let orderStatus: OrderStatus

    var body: some View {
        if index < currentStep {
            DoneStep(orderStatus: orderStatus)
        } else if index == currentStep {
            OrderChip(orderStatus: orderStatus)
        } else {
            FutureStep()
        }
    }
}

struct DoneStep: View {
    let orderStatus: OrderStatus

    var body: some View {
        IconImage(width: 12, height: 10, imageName: "CheckIcon")
            .padding(Diems.SMALL_PADDING)
            .padding(.horizontal, 16)
            .background(OrderChip.getColor(status: orderStatus))
            .foregroundColor(AppColor.surface)
            .cornerRadius(16)
    }
}

struct FutureStep: View {
    var body: some View {
        IconImage(width: 12, height: 10, imageName: "CheckIcon")
            .padding(Diems.SMALL_PADDING)
            .padding(.horizontal, 8)
            .background(AppColor.onSurfaceVariant)
            .foregroundColor(AppColor.onSurfaceVariant)
            .cornerRadius(16)
    }
}

struct OrderStatusBar_Previews: PreviewProvider {
    static var previews: some View {
        OrderStatusBar(orderStatus: OrderStatus.accepted, orderStatusName: "Принят")
    }
}
