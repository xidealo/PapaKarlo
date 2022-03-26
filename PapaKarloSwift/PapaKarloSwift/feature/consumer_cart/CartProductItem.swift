//
//  CartProductItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI

struct CartProductItem: Identifiable {
    var id = UUID()
    let name:String
    let newCost:String
    let oldCost:String?
    let photoLink:String
    let count:Int
    let menuProductUuid:String
}
