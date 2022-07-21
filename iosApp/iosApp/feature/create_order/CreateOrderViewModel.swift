//
//  CreateOrderViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 13.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
class CreateOrderViewModel:ObservableObject {
    
    @Published var creationOrderViewState = CreateOrderViewState(isDelivery: true, address: nil, comment: nil, deferredTime: "", totalCost: "", deliveryCost: "", amountToPay: "", amountToPayWithDeliveryCost: "", isLoading: true)
    
    
    init(){
        iosComponent.provideCartProductInteractor().getCartTotal { cartTotal, error in
            if(cartTotal==nil) {
                self.creationOrderViewState = CreateOrderViewState(isDelivery: self.creationOrderViewState.isDelivery, address: self.creationOrderViewState.address, comment: self.creationOrderViewState.comment, deferredTime: self.creationOrderViewState.deferredTime, totalCost: "", deliveryCost: "", amountToPay:  "", amountToPayWithDeliveryCost:  "", isLoading: false)
                return
            }
                
            
            self.creationOrderViewState = CreateOrderViewState(isDelivery: self.creationOrderViewState.isDelivery, address: self.creationOrderViewState.address, comment: self.creationOrderViewState.comment, deferredTime: self.creationOrderViewState.deferredTime, totalCost: String(cartTotal!.totalCost), deliveryCost: String(cartTotal!.deliveryCost), amountToPay: String(cartTotal!.amountToPay), amountToPayWithDeliveryCost:  String(cartTotal!.amountToPayWithDeliveryCost), isLoading: false)
        }
    }
    
}
