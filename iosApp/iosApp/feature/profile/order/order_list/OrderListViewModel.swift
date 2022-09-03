//
//  OrderListViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class OrderListViewModel : ObservableObject{
    
    @Published var orderListViewState = OrderListViewState(isLoading: false, orderList: [])

    init(){
        iosComponent.provideIOrderInteractor().observeOrderListSwift().watch { orders in
            self.orderListViewState = OrderListViewState(isLoading: false, orderList: orders!.map { order in
                OrderItem(id: (order as! LightOrder).uuid, status: (order as! LightOrder).status.name, code: (order as! LightOrder).code, dateTime: "")
            })
        }

    }
}
