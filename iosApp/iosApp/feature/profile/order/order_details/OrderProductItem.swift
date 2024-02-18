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
    var newCost: String
    var photoLink: String
    var count: String
    var additions:String
    var isLast:Bool
    
    init(
        id:String,
        name:String,
        newPrice:String,
        newCost:String,
        photoLink:String,
        count:String,
        additions:String,
        isLast:Bool
    ){
        self.id = id
        self.name = name
        self.newPrice = newPrice
        self.newCost = newCost
        self.photoLink = photoLink
        self.count = count
        self.additions = additions
        self.isLast = isLast
    }
}
