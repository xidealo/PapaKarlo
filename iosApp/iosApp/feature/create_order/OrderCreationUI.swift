//
//  OrderCreationUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import Foundation

struct OrderCreationUI {
    let isDelivery:Bool
    let address:String?
    let comment: String?
    let deferredTime: String
    let totalCost: String
    let deliveryCost: String
    let amountToPay: String
    let isLoading: Bool
    
    
    func switchPosition(isDelivery:Bool) -> Int {
        if (isDelivery) {
            return 0
        } else {
            return 1
        }
    }
}
