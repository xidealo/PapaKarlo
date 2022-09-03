//
//  ConsumerCartUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import Foundation

class ConsumerCartViewState: NSObject, NSCopying {
    var forFreeDelivery:String
    var cartProductList: [CartProductItem]
    var oldTotalCost:Int?
    var newTotalCost:String
    var consumerCartState:ConsumerCartState
    
    init(forFreeDelivery:String, cartProductList: [CartProductItem], oldTotalCost:Int?, newTotalCost:String, consumerCartState:ConsumerCartState){
        self.forFreeDelivery = forFreeDelivery
        self.cartProductList = cartProductList
        self.oldTotalCost = oldTotalCost
        self.newTotalCost = newTotalCost
        self.consumerCartState = consumerCartState
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = ConsumerCartViewState(forFreeDelivery: forFreeDelivery, cartProductList: cartProductList, oldTotalCost: oldTotalCost, newTotalCost: newTotalCost, consumerCartState: consumerCartState)
        return copy
    }
}
enum ConsumerCartState{
    case empty, notAuthorized, hasData, loading, goToCreateOrder, goToLogin
}
