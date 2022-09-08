//
//  OrderDetailsViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class OrderDetailsViewState: NSObject, NSCopying {
    var code:           String
    var status:         OrderStatus
    var statusName:     String
    var dateTime:       String
    var pickupMethod:   String
    var deferredTimeHintStringId: String
    var deferredTime:   String?
    var address:        String
    var comment:        String?
    var deliveryCost:   String?
    var isDelivery:     Bool
    var oldAmountToPay: String?
    var newAmountToPay: String
    
    //var orderProductList: [OrderProductItem]
    
    init(code: String, status: OrderStatus, statusName: String, dateTime: String, pickupMethod: String, deferredTimeHintStringId:String, deferredTime: String?, address: String, comment: String?, deliveryCost: String?, isDelivery: Bool, oldAmountToPay: String?,newAmountToPay: String){
        
        self.code =             code
        self.status =           status
        self.statusName =       statusName
        self.dateTime =         dateTime
        self.pickupMethod =     pickupMethod
        self.deferredTimeHintStringId = deferredTimeHintStringId
        self.deferredTime =     deferredTime
        self.address =          address
        self.comment =          comment
        self.deliveryCost =     deliveryCost
        self.isDelivery =       isDelivery
        self.oldAmountToPay =   oldAmountToPay
        self.newAmountToPay =   newAmountToPay
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = OrderDetailsViewState(
            code:code,
            status:status,
            statusName:statusName,
            dateTime:dateTime,
            pickupMethod:pickupMethod,
            deferredTimeHintStringId:deferredTimeHintStringId,
            deferredTime:deferredTime,
            address:address,
            comment:comment,
            deliveryCost:deliveryCost,
            isDelivery:isDelivery,
            oldAmountToPay:oldAmountToPay,
            newAmountToPay:newAmountToPay)
        return copy
    }
}
