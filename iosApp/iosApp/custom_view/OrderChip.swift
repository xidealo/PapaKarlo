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
            .labelSmall()
            .foregroundColor(AppColor.surface)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .background(OrderChip.getColor(status: orderStatus))
            .cornerRadius(12)
    }
    
    static func getColor(status:OrderStatus) -> Color{
        switch status{
        case OrderStatus.notAccepted : return Color("notAcceptedColor")
        case OrderStatus.accepted : return Color("acceptedColor")
        case OrderStatus.preparing : return Color("preparingColor")
        case OrderStatus.sentOut : return Color("sentOutColor")
        case OrderStatus.done : return Color("doneColor")
        case OrderStatus.delivered : return Color("deliveredColor")
        case OrderStatus.canceled : return Color("canceledColor")
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
