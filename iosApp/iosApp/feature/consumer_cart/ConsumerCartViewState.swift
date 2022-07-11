//
//  ConsumerCartUI.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import Foundation

struct ConsumerCartViewState {
    let forFreeDelivery:String
    let cartProductList: [CartProductItem]
    let oldTotalCost:Int?
    let newTotalCost:String
}
