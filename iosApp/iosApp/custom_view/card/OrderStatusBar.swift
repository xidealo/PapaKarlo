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
    var currentStep = 0

    init(orderStatus: OrderStatus) {
        self.orderStatus = orderStatus
        currentStep = getCurrentStep(orderStatus: orderStatus)
    }

    var body: some View {
        VStack(spacing: 0){
            HStack(spacing: 0){
                IconImage(
                    width: 24,
                    height: 24,
                    imageName: getOrderIcon(orderStatus: orderStatus)
                ).foregroundColor(AppColor.onSurfaceVariant)
                
                Text(OrderChip.getStatusName(status: orderStatus))
                    .bodyMedium()
                    .padding(.leading, 8)

                Spacer()
            }
            
            HStack(spacing: Diems.HALF_SMALL_PADDING) {
                ForEach(0 ... 4, id: \.self) { i in
                    Step(
                        index: i,
                        currentStep: getCurrentStep(orderStatus: orderStatus),
                        orderStatus: orderStatus
                    )
                }
            }.padding(.vertical, Diems.SMALL_PADDING)
        }
        .frame(maxWidth: .infinity)
        .background(AppColor.surface)
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
    func getOrderIcon(orderStatus: OrderStatus) -> String {
        switch orderStatus {
        case OrderStatus.notAccepted: return "NotAcceptedIcon"
        case OrderStatus.accepted: return "AcceptedIcon"
        case OrderStatus.preparing: return "PrepairingIcon"
        case OrderStatus.sentOut: return "DeliveringIcon"
        case OrderStatus.done: return "DoneIcon"
        case OrderStatus.delivered: return "DeliveredIcon"
        case OrderStatus.canceled: return "CanceledIcon"
        default: return "NotAcceptedIcon"
        }
    }
}

struct Step: View {
    let index: Int
    let currentStep: Int
    let orderStatus: OrderStatus

    var body: some View {
        if index <= currentStep {
            DoneStep(orderStatus: orderStatus)
        } else {
            FutureStep()
        }
    }
}

struct DoneStep: View {
    let orderStatus: OrderStatus
    
    var body: some View {
        ZStack{
            IconImage(width: 12, height: 16, imageName: "CheckIcon")
                .foregroundColor(AppColor.surface)
        }.frame(maxWidth: .infinity)
            .background(OrderChip.getColor(status: orderStatus))
            .cornerRadius(16)
    }
}

struct FutureStep: View {
    var body: some View {
        Spacer()
            .frame(maxHeight: 16)
            .background(AppColor.onSurfaceVariant)
            .foregroundColor(AppColor.onSurfaceVariant)
            .cornerRadius(16)
        
    }
}

struct OrderStatusBar_Previews: PreviewProvider {
    static var previews: some View {
        OrderStatusBar(orderStatus: OrderStatus.accepted)
    }
}
