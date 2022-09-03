//
//  OrderViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class OrderListViewState :NSObject {
    
    let isLoading:Bool
    let orderList:[OrderItem]
    
    init(isLoading:Bool, orderList:[OrderItem]){
        self.isLoading = isLoading
        self.orderList = orderList
    }
    
}
