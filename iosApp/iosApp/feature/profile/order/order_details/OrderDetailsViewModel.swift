//
//  OrderDetailsViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 12.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class OrderDetailsViewModel :ObservableObject {

    @Published var orderDetailsViewState:OrderDetailsViewState = OrderDetailsViewState(
        code: "",
        status: OrderStatus.notAccepted,
        statusName: "",
        dateTime: "",
        pickupMethod: "",
        deferredTimeHintString: "",
        deferredTime: nil,
        address: "",
        comment: nil,
        deliveryCost: nil,
        isDelivery: false,
        oldAmountToPay: nil,
        newAmountToPay: "",
        orderProductList: []
    )


    let dateUtil = DateUtil()
    
    init(uuid:String){
        iosComponent.provideIOrderInteractor().observeOrderByUuid(orderUuid: uuid).watch { orderWithAmout in
            (self.orderDetailsViewState.copy() as! OrderDetailsViewState).apply { copiedState in
                copiedState.code = orderWithAmout?.code ?? ""
                copiedState.status = orderWithAmout?.status ?? OrderStatus.accepted
                copiedState.statusName = OrderChip.getStatusName(status: (orderWithAmout?.status ?? OrderStatus.accepted))
                
                if(orderWithAmout?.dateTime != nil){
                    copiedState.dateTime = self.dateUtil.getDateTimeString(dateTime: orderWithAmout!.dateTime)
                }
                
                if(orderWithAmout?.isDelivery == true){
                    copiedState.pickupMethod = "Доставка"
                    copiedState.deferredTimeHintString = "Ко времени"

                }else{
                    copiedState.pickupMethod = "Самовывоз"
                    copiedState.deferredTimeHintString = "Ко времени"
                }
                
                if(orderWithAmout?.deferredTime != nil){
                    copiedState.deferredTime = self.dateUtil.getTimeString(time: orderWithAmout!.deferredTime!)
                }

                copiedState.address = orderWithAmout?.address ?? ""
                copiedState.comment = orderWithAmout?.comment ?? ""

                copiedState.isDelivery = orderWithAmout?.isDelivery ?? false
                if(orderWithAmout?.oldAmountToPay != nil){
                    copiedState.oldAmountToPay =  "\(orderWithAmout?.oldAmountToPay ?? 0)\(Strings.CURRENCY)"
                }
                copiedState.newAmountToPay = "\(orderWithAmout?.newAmountToPay ?? 0)\(Strings.CURRENCY)"

                copiedState.orderProductList = orderWithAmout?.orderProductList.map({ orderProductsWithCosts in
                    var oldPrice:String? = nil
                    var oldCost:String? = nil
                    
                    if(orderProductsWithCosts.product.oldPrice != nil){
                        oldPrice = "\(orderProductsWithCosts.product.oldPrice ?? 0)\(Strings.CURRENCY)"
                    }
                    
                    if(orderProductsWithCosts.oldCost != nil){
                        oldCost = "\(orderProductsWithCosts.oldCost ?? 0)\(Strings.CURRENCY)"
                    }
                    
                    return OrderProductItem(id: orderProductsWithCosts.uuid,
                                     name: orderProductsWithCosts.product.name,
                                     newPrice: "\(orderProductsWithCosts.product.newPrice)\(Strings.CURRENCY)",
                                     oldPrice: oldPrice,
                                     newCost: "\(orderProductsWithCosts.newCost)\(Strings.CURRENCY)",
                                     oldCost: oldCost,
                                     photoLink: orderProductsWithCosts.product.photoLink,
                                     count: "× \(orderProductsWithCosts.count)")
                }) ?? []
                if(orderWithAmout?.deliveryCost != nil){
                    copiedState.deliveryCost = "\(orderWithAmout?.deliveryCost ?? 0)\(Strings.CURRENCY)"
                }
                self.orderDetailsViewState = copiedState
            }
        }
    }

}
