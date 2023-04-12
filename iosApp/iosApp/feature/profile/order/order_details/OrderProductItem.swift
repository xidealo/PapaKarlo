//
//  OrderProductItem.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class OrderProductItem: NSObject, Identifiable {
    
    var id: String
    var name: String
    var newPrice: String
    var oldPrice: String?
    var newCost: String
    var oldCost: String?
    var photoLink: String
    var count: String
    
    init(
        id:String,
        name:String,
        newPrice:String,
        oldPrice:String?,
        newCost:String,
        oldCost:String?,
        photoLink:String,
        count:String
    ){
        self.id = id
        self.name = name
        self.newPrice = newPrice
        self.oldPrice = oldPrice
        self.newCost = newCost
        self.oldCost = oldCost
        self.photoLink = photoLink
        self.count = count
    }
}
