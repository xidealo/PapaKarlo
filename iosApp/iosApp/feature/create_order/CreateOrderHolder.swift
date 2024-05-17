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
    
    @Published var creationOrderViewState = CreateOrderUIState(
        isDelivery: true,
        deliveryAddress: nil,
        isDeliveryAddressErrorShown: false,
        pickupAddress: nil,
        comment: nil,
        deferredTime: nil,
        totalCost: nil,
        deliveryCost: nil,
        oldFinalCost: nil,
        newFinalCost: nil,
        isLoading: false,
        eventList: [],
        paymentMethod: nil,
        discount: nil
    )
    
    let kmmViewModel = CreateOrderViewModel(
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        cafeInteractor: iosComponent.provideCafeInteractor(),
        userInteractor: iosComponent.provideIUserInteractor(),
        createOrderStateMapper: iosComponent.provideCreateOrderStateMapper(),
        getSelectableUserAddressList: iosComponent.provideGetSelectableUserAddressListUseCase(),
        getSelectableCafeList: iosComponent.provideGetSelectableCafeListUseCase(),
        getCartTotal: iosComponent.provideGetCartTotalUseCase(),
        getMinTime: iosComponent.provideGetMinTimeUseCase(),
        createOrder: iosComponent.provideCreateOrderUseCase(),
        getSelectedCityTimeZone: iosComponent.provideGetSelectedCityTimeZoneUseCase(),
        saveSelectedUserAddress : iosComponent.provideSaveSelectedUserAddressUseCase(),
        getSelectablePaymentMethodListUseCase : iosComponent.provideGetSelectablePaymentMethodListUseCase(),
        savePaymentMethodUseCase : iosComponent.provideSavePaymentMethodUseCase()
    )
        
    var listener: Closeable? = nil
    
    func update(){
        kmmViewModel.update()
        listener = kmmViewModel.uiState.watch { orderCreationUiState in
            if let checkedOrderCreationUiState = orderCreationUiState {
                self.creationOrderViewState = checkedOrderCreationUiState
            }
        }
    }
    
    func removeListener(){
        listener?.close()
        listener = nil
    }
    
    //todo remove
    func getUserAddressList() -> String {
        if(creationOrderViewState.deliveryAddress == nil){
            return ""
        }
        
        var address : String = creationOrderViewState.deliveryAddress?.address.street ?? ""
        
        if(creationOrderViewState.deliveryAddress?.address.house != nil){
            address += ", д. " + (creationOrderViewState.deliveryAddress?.address.house ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.address.flat != nil && creationOrderViewState.deliveryAddress?.address.flat != ""){
            address += ", кв. " + (creationOrderViewState.deliveryAddress?.address.flat ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.address.entrance != nil && creationOrderViewState.deliveryAddress?.address.entrance != ""){
            address += ", подъезд " + (creationOrderViewState.deliveryAddress?.address.entrance ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.address.floor != nil && creationOrderViewState.deliveryAddress?.address.floor != ""){
            address += ", этаж. " + (creationOrderViewState.deliveryAddress?.address.floor ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.address.comment != nil && creationOrderViewState.deliveryAddress?.address.comment != ""){
            address += ", \(creationOrderViewState.deliveryAddress?.address.comment ?? "")"
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
    
    func onPaymentMethodClick()  {
        kmmViewModel.onPaymentMethodClick()
    }
}
