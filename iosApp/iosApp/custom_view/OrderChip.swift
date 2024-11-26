//
//  OrderChip.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct OrderChip: View {
    let orderStatus:OrderStatus
    
    var body: some View {
        Text(OrderChip.getStatusName(status: orderStatus))
            .labelSmall(weight: .medium)
            .foregroundColor(AppColor.onOrder)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
            .background(OrderChip.getColor(status: orderStatus))
            .cornerRadius(16)
    }
    
    static func getColor(status:OrderStatus) -> Color{
        switch status{
        case OrderStatus.notAccepted : return AppColor.notAccepted
        case OrderStatus.accepted : return AppColor.accepted
        case OrderStatus.preparing : return AppColor.preparing
        case OrderStatus.sentOut : return AppColor.sentOut
        case OrderStatus.done : return AppColor.done
        case OrderStatus.delivered : return AppColor.delivered
        case OrderStatus.canceled : return AppColor.canceled
        default : return Color.blue
        }
    }
    
    static func getStatusName(status:OrderStatus) -> String{
        switch status{
        case OrderStatus.notAccepted : return Strings.MSG_STATUS_NOT_ACCEPTED
        case OrderStatus.accepted : return Strings.MSG_STATUS_ACCEPTED
        case OrderStatus.preparing : return Strings.MSG_STATUS_PREPARING
        case OrderStatus.sentOut : return Strings.MSG_STATUS_SENT_OUT
        case OrderStatus.done : return Strings.MSG_STATUS_DONE
        case OrderStatus.delivered : return Strings.MSG_STATUS_DELIVERED
        case OrderStatus.canceled : return Strings.MSG_STATUS_CANCELED
        default : return Strings.MSG_STATUS_NOT_ACCEPTED
        }
    }
}

struct OrderChip_Previews: PreviewProvider {
    static var previews: some View {
        OrderChip(orderStatus: OrderStatus.notAccepted)
    }
}
