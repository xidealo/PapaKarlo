//
//  CreateOrderViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 13.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class CreateOrderHolder: ObservableObject {
    
    @Published var creationOrderViewState = OrderCreationState(
        isDelivery: true,
        deliveryAddress: nil,
        pickupAddress: nil,
        comment: nil,
        deferredTime: TimeUIASAP(),
        totalCost: nil,
        deliveryCost: nil,
        finalCost: nil,
        isAddressErrorShown: false,
        isLoading: false,
        eventList: []
    )
    
    let kmmViewModel = CreateOrderViewModel(
        addressInteractor: iosComponent.provideIAddressInteractor(),
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        cafeInteractor: iosComponent.provideCafeInteractor(),
        userInteractor: iosComponent.provideIUserInteractor(),
        timeMapper: iosComponent.provideTimeMapper(),
        userAddressMapper: iosComponent.provideUserAddressMapper(),
        getSelectedUserAddress: iosComponent.provideGetSelectedUserAddressUseCase(),
        getSelectedCafe: iosComponent.provideGetSelectedCafeUseCase(),
        getUserAddressList: iosComponent.provideGetUserAddressListUseCase(),
        getCafeList: iosComponent.provideGetCafeListUseCase(),
        getCartTotal: iosComponent.provideGetCartTotalUseCase(),
        getMinTime: iosComponent.provideGetMinTimeUseCase(),
        createOrderUseCase : iosComponent.provideCreateOrderUseCase(),
        getSelectedCityTimeZoneUseCase: iosComponent.provideGetSelectedCityTimeZoneUseCase()
    )
    
    init(){
        kmmViewModel.orderCreationState.watch { orderCreationUiState in
            if let checkedOrderCreationUiState = orderCreationUiState {
                self.creationOrderViewState = checkedOrderCreationUiState
            }
        }
    }
    
    func getUserAddressList() -> String {
        
        if(creationOrderViewState.deliveryAddress == nil){
            return ""
        }
        
        var address : String = creationOrderViewState.deliveryAddress?.street ?? ""
        
        if(creationOrderViewState.deliveryAddress?.house != nil){
            address += " д. " + (creationOrderViewState.deliveryAddress?.house ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.flat != nil && creationOrderViewState.deliveryAddress?.flat != ""){
            address += " кв. " + (creationOrderViewState.deliveryAddress?.flat ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.entrance != nil && creationOrderViewState.deliveryAddress?.entrance != ""){
            address += " подъезд " + (creationOrderViewState.deliveryAddress?.entrance ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.floor != nil && creationOrderViewState.deliveryAddress?.floor != ""){
            address += " этаж. " + (creationOrderViewState.deliveryAddress?.floor ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.comment != nil && creationOrderViewState.deliveryAddress?.comment != ""){
            address += (creationOrderViewState.deliveryAddress?.comment ?? "")
        }
        
        return address
    }
    
    func createOrder(){
        kmmViewModel.onCreateOrderClicked()
    }
    
    func goToAddress(){
        if(creationOrderViewState.isDelivery){
            kmmViewModel.onUserAddressClicked()
        }else{
            kmmViewModel.onCafeAddressClicked()
        }
    }
}
