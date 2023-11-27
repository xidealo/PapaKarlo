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
        
        var address : String = creationOrderViewState.deliveryAddress?.street.name ?? ""
        
        if(creationOrderViewState.deliveryAddress?.house != nil){
            address += ", д. " + (creationOrderViewState.deliveryAddress?.house ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.flat != nil && creationOrderViewState.deliveryAddress?.flat != ""){
            address += ", кв. " + (creationOrderViewState.deliveryAddress?.flat ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.entrance != nil && creationOrderViewState.deliveryAddress?.entrance != ""){
            address += ", подъезд " + (creationOrderViewState.deliveryAddress?.entrance ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.floor != nil && creationOrderViewState.deliveryAddress?.floor != ""){
            address += ", этаж. " + (creationOrderViewState.deliveryAddress?.floor ?? "")
        }
        
        if(creationOrderViewState.deliveryAddress?.comment != nil && creationOrderViewState.deliveryAddress?.comment != ""){
            address += ", \(creationOrderViewState.deliveryAddress?.comment ?? "")"
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
