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
    
    @Published var creationOrderViewState = CreateOrderViewState(
        isDelivery: true,
        address: nil,
        comment: "",
        deferredTime: Date.now + 60 * 60,
        totalCost: "",
        deliveryCost: "",
        amountToPay: "",
        amountToPayWithDeliveryCost: "",
        userUuid: nil,
        cafeUuid: nil,
        createOrderState: CreateOrderState.loading,
        notNeedDeferredTime : true,
        actionList: []
    )
    
    init(){
        iosComponent.provideCafeInteractor().getCafeList { cafes, err in
            print("cafe loaded")
        }
    }
    
    func loadData(){
        print("CreateOrderViewModel")
        iosComponent.provideCartProductInteractor().getCartTotal { cartTotal, error in
            if(cartTotal==nil) {
                self.creationOrderViewState = CreateOrderViewState(
                    isDelivery: self.creationOrderViewState.isDelivery,
                    address: self.creationOrderViewState.address,
                    comment: self.creationOrderViewState.comment,
                    deferredTime: self.creationOrderViewState.deferredTime,
                    totalCost: "",
                    deliveryCost: "",
                    amountToPay:  "",
                    amountToPayWithDeliveryCost:  "",
                    userUuid: self.creationOrderViewState.userUuid,
                    cafeUuid: self.creationOrderViewState.cafeUuid,
                    createOrderState: self.creationOrderViewState.createOrderState,
                    notNeedDeferredTime: self.creationOrderViewState.notNeedDeferredTime,
                    actionList: self.creationOrderViewState.actionList
                )
                return
            }
            self.creationOrderViewState = CreateOrderViewState(
                isDelivery: self.creationOrderViewState.isDelivery,
                address: self.creationOrderViewState.address,
                comment: self.creationOrderViewState.comment,
                deferredTime: self.creationOrderViewState.deferredTime,
                totalCost: String(cartTotal!.totalCost) + Strings.CURRENCY,
                deliveryCost: String(cartTotal!.deliveryCost) + Strings.CURRENCY,
                amountToPay: String(cartTotal!.amountToPay) + Strings.CURRENCY,
                amountToPayWithDeliveryCost:  String(cartTotal!.amountToPayWithDeliveryCost) +  Strings.CURRENCY,
                userUuid: self.creationOrderViewState.userUuid,
                cafeUuid: self.creationOrderViewState.cafeUuid,
                createOrderState: self.creationOrderViewState.createOrderState,
                notNeedDeferredTime: self.creationOrderViewState.notNeedDeferredTime,
                actionList: self.creationOrderViewState.actionList
            )
        }
        getAddressList(isDelivery: creationOrderViewState.isDelivery)
    }
    
    func getAddressList(isDelivery:Bool){
        if(isDelivery){
            iosComponent.provideIAddressInteractor().observeAddress().watch { userAddress in
                if(userAddress == nil){
                    (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                        copiedState.address = nil
                        copiedState.createOrderState = CreateOrderState.success
                        self.creationOrderViewState = copiedState
                    }
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
                
                (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                    copiedState.address = address
                    copiedState.createOrderState = CreateOrderState.success
                    self.creationOrderViewState = copiedState
                }
            }
        }else{
            iosComponent.provideCafeInteractor().observeSelectedCafeAddress().watch { cafeAddress in
                (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                    copiedState.address = cafeAddress?.address ?? ""
                    copiedState.cafeUuid = cafeAddress?.cafeUuid
                    copiedState.createOrderState = CreateOrderState.success
                    self.creationOrderViewState = copiedState
                }
            }
        }
    }
    
    func createOrder(){
        
        if(creationOrderViewState.isDelivery && creationOrderViewState.userUuid == nil){
            (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                copiedState.actionList.append(CreateOrderAction.showAddressError)
                creationOrderViewState = copiedState
            }
            return
        }
        
        if(!creationOrderViewState.isDelivery && creationOrderViewState.cafeUuid == nil){
            (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                copiedState.createOrderState = CreateOrderState.addressError
                creationOrderViewState = copiedState
            }
            return
        }
        
        (creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
            copiedState.createOrderState = CreateOrderState.loading
            creationOrderViewState = copiedState
        }
  
        var defTime: KotlinLong? = nil
        
        if(!creationOrderViewState.notNeedDeferredTime){
            defTime = KotlinLong(value: Int64(creationOrderViewState.deferredTime.timeIntervalSince1970 * 1000.0))
        }
        iosComponent.provideIOrderInteractor().createOrder(isDelivery: creationOrderViewState.isDelivery, userAddressUuid: creationOrderViewState.userUuid, cafeUuid: creationOrderViewState.cafeUuid, addressDescription: creationOrderViewState.address ?? "", comment: creationOrderViewState.comment, deferredTime: defTime) { code, err in
            if(code == nil){
                //show error
                (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                    copiedState.createOrderState = CreateOrderState.commonError
                    self.creationOrderViewState = copiedState
                }
            }else{
                DispatchQueue.main.async {
                    iosComponent.provideCartProductInteractor().removeAllProductsFromCart { err in
                        (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                            copiedState.createOrderState = CreateOrderState.goToProfile
                            self.creationOrderViewState = copiedState
                        }
                    }
                }
            }
        }
    }
    
    func goToAddress(){
        if(creationOrderViewState.isDelivery){
            (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                copiedState.createOrderState = CreateOrderState.goToUserAddressList
                self.creationOrderViewState = copiedState
            }
        }else{
            (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
                copiedState.createOrderState = CreateOrderState.goToCafeAddressList
                self.creationOrderViewState = copiedState
            }
        }
    }
    
    func clearActions(){
        (self.creationOrderViewState.copy() as! CreateOrderViewState).apply { copiedState in
            copiedState.actionList = []
            self.creationOrderViewState = copiedState
        }
    }
}

enum CreateOrderState{
    case success, loading, goToUserAddressList, goToCafeAddressList, goToProfile, commonError, addressError
}

enum CreateOrderAction {
   case showCommonError, showAddressError
}
