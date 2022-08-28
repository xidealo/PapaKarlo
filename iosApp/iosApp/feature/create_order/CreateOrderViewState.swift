//
//  OrderCreationUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import Foundation

class CreateOrderViewState: NSObject, NSCopying {
    
    var isDelivery:Bool
    var address:String?
    var comment: String?
    var deferredTime: String
    var totalCost: String
    var deliveryCost: String
    var amountToPay: String
    var amountToPayWithDeliveryCost: String
    var isLoading: Bool
    var userUuid:String?
    var cafeUuid:String?
    var isGoProfile:Bool = false
    
    init(isDelivery:Bool, address:String?, comment:String?, deferredTime:String,totalCost: String, deliveryCost: String, amountToPay:String,amountToPayWithDeliveryCost: String,isLoading: Bool, userUuid:String?,cafeUuid:String?,isGoProfile:Bool){
        self.isDelivery = isDelivery
        self.address = address
        self.comment = comment
        self.deferredTime = deferredTime
        self.totalCost = totalCost
        self.deliveryCost = deliveryCost
        self.amountToPay = amountToPay
        self.amountToPayWithDeliveryCost = amountToPayWithDeliveryCost
        self.isLoading = isLoading
        self.userUuid = userUuid
        self.cafeUuid = cafeUuid
        self.isGoProfile = isGoProfile
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = CreateOrderViewState(isDelivery : isDelivery, address : address, comment : comment, deferredTime : deferredTime, totalCost : totalCost, deliveryCost : deliveryCost, amountToPay : amountToPay, amountToPayWithDeliveryCost : amountToPayWithDeliveryCost, isLoading : isLoading, userUuid : userUuid, cafeUuid : cafeUuid, isGoProfile : isGoProfile)
            return copy
        }
    
    func switchPosition(isDelivery:Bool) -> Int {
        if (isDelivery) {
            return 0
        } else {
            return 1
        }
    }
}
