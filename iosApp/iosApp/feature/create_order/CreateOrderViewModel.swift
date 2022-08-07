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
        getAddressList(isDelivery: true)
    }
    
    func getAddressList(isDelivery:Bool){
        
        if(isDelivery){
            iosComponent.provideIAddressInteractor().observeAddress().watch { userAddress in
                if(userAddress == nil){
                    return
                }
                var address = userAddress?.street.name ?? ""
                if(userAddress?.house != nil){
                    address += " д. " + (userAddress?.house ?? "")
                }
                
                if(userAddress?.flat != nil && userAddress?.flat != ""){
                    address += " кв. " + (userAddress?.flat ?? "")
                }
                
                if(userAddress?.entrance != nil && userAddress?.entrance != ""){
                    address += " подъезд " + (userAddress?.entrance ?? "")
                }

                if(userAddress?.floor != nil && userAddress?.floor != ""){
                    address += " этаж. " + (userAddress?.floor ?? "")
                }
                
                if(userAddress?.comment != nil && userAddress?.comment != ""){
                    address += (userAddress?.comment ?? "")
                }
                
                print(address)
                self.creationOrderViewState = CreateOrderViewState(isDelivery: self.creationOrderViewState.isDelivery, address: address, comment: self.creationOrderViewState.comment, deferredTime: self.creationOrderViewState.deferredTime, totalCost: self.creationOrderViewState.totalCost, deliveryCost: self.creationOrderViewState.deliveryCost, amountToPay: self.creationOrderViewState.amountToPay, amountToPayWithDeliveryCost:  self.creationOrderViewState.amountToPayWithDeliveryCost, isLoading: false)
            }
        }else{
            iosComponent.provideCafeInteractor().observeSelectedCafeAddress().watch { cafeAddress in
                self.creationOrderViewState = CreateOrderViewState(isDelivery: self.creationOrderViewState.isDelivery, address: cafeAddress?.address ?? "", comment: self.creationOrderViewState.comment, deferredTime: self.creationOrderViewState.deferredTime, totalCost: self.creationOrderViewState.totalCost, deliveryCost: self.creationOrderViewState.deliveryCost, amountToPay: self.creationOrderViewState.amountToPay, amountToPayWithDeliveryCost:  self.creationOrderViewState.amountToPayWithDeliveryCost, isLoading: false)
            }
        }
        
    }
    
}
