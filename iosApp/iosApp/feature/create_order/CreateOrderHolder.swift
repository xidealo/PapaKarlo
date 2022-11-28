//
//  CreateOrderViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 13.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
class CreateOrderHolder:ObservableObject {
    
    @Published var creationOrderViewState = OrderCreationUiState(
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
        orderInteractor: iosComponent.provideIOrderInteractor(),
        cafeInteractor: iosComponent.provideCafeInteractor(),
        userInteractor: iosComponent.provideIUserInteractor(),
        deferredTimeInteractor: iosComponent.provideIDeferredTimeInteractor(),
        timeMapper: iosComponent.provideTimeMapper(),
        userAddressMapper: iosComponent.provideUserAddressMapper(),
        getSelectedUserAddress: iosComponent.provideGetSelectedUserAddressUseCase(),
        getSelectedCafe: iosComponent.provideGetSelectedCafeUseCase(),
        getUserAddressList: iosComponent.provideGetUserAddressListUseCase(),
        getCafeList: iosComponent.provideGetCafeListUseCase(),
        getCartTotal: iosComponent.provideGetCartTotalUseCase(),
        getMinTime: iosComponent.provideGetMinTimeUseCase()
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
        
        //        if(creationOrderViewState.isDelivery && creationOrderViewState.userUuid == nil){
        //            (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                copiedState.actionList.append(CreateOrderAction.showAddressError)
        //                copiedState.createOrderState = CreateOrderState.success
        //                creationOrderViewState = copiedState
        //            }
        //            return
        //        }
        //
        //        if(!creationOrderViewState.isDelivery && creationOrderViewState.cafeUuid == nil){
        //            (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                copiedState.actionList.append(CreateOrderAction.showAddressError)
        //                copiedState.createOrderState = CreateOrderState.success
        //                creationOrderViewState = copiedState
        //            }
        //            return
        //        }
        
        //        (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //            copiedState.createOrderState = CreateOrderState.loading
        //            creationOrderViewState = copiedState
        //        }
        
        var defTime: KotlinLong? = nil
        
        //        if(!creationOrderViewState.notNeedDeferredTime){
        //            defTime = KotlinLong(value: Int64(creationOrderViewState.deferredTime.timeIntervalSince1970 * 1000.0))
        //        }
        //        iosComponent.provideIOrderInteractor().createOrder(
        //            isDelivery: creationOrderViewState.isDelivery, userAddressUuid: creationOrderViewState.userUuid, cafeUuid: creationOrderViewState.cafeUuid, addressDescription: creationOrderViewState.address ?? "", comment: creationOrderViewState.comment, deferredTime: 111) { code, err in
        //            if(code == nil){
        //                //show error
        //                (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                    copiedState.actionList.append(CreateOrderAction.showCommonError)
        //                    copiedState.createOrderState = CreateOrderState.success
        //                    self.creationOrderViewState = copiedState
        //                }
        //            }else{
        //                DispatchQueue.main.async {
        //                    iosComponent.provideCartProductInteractor().removeAllProductsFromCart { err in
        //                        (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                            copiedState.createOrderState = CreateOrderState.success
        //                            copiedState.actionList.append(CreateOrderAction.goToProfile)
        //                            self.creationOrderViewState = copiedState
        //                        }
        //                    }
        //                }
        //            }
        //        }
    }
    
    func goToAddress(){
        //        if(creationOrderViewState.isDelivery){
        //            (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                copiedState.actionList.append(CreateOrderAction.goToUserAddressList)
        //                self.creationOrderViewState = copiedState
        //            }
        //        }else{
        //            (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //                copiedState.actionList.append(CreateOrderAction.goToCafeAddressList)
        //                self.creationOrderViewState = copiedState
        //            }
        //        }
    }
    
    func clearActions(){
        //        (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
        //            copiedState.actionList = []
        //            self.creationOrderViewState = copiedState
        //        }
    }
}

enum CreateOrderState{
    case success, loading
}

enum CreateOrderAction {
    case showCommonError, showAddressError, goToCafeAddressList, goToUserAddressList, goToProfile
}
