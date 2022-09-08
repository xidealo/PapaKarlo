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

    @Published var orderDetailsViewState:OrderDetailsViewState = OrderDetailsViewState(code: "", status: OrderStatus.notAccepted, statusName: "", dateTime: "", pickupMethod: "", deferredTimeHintStringId: "", deferredTime: nil, address: "", comment: nil, deliveryCost: nil, isDelivery: false, oldAmountToPay: nil, newAmountToPay: "")


    init(uuid:String){
        iosComponent.provideIOrderInteractor().observeOrderByUuid(orderUuid: uuid).watch { orderWithAmout in
            (self.orderDetailsViewState.copy() as! OrderDetailsViewState).apply { copiedState in
                copiedState.code = orderWithAmout?.code ?? ""
                copiedState.status = orderWithAmout?.status ?? OrderStatus.accepted
                copiedState.statusName = getStatusName(status: (orderWithAmout?.status ?? OrderStatus.accepted).name)
                copiedState.dateTime = "todo"
                if(orderWithAmout?.isDelivery == true){
                    copiedState.pickupMethod = "Доставка"
                    copiedState.deferredTimeHintStringId = "Ко времени"

                }else{
                    copiedState.pickupMethod = "Самовывоз"
                    copiedState.deferredTimeHintStringId = "Ко времени"
                }
                //copiedState.deferredTime = orderWithAmout?.address ?? ""

                copiedState.address = orderWithAmout?.address ?? ""
                copiedState.comment = orderWithAmout?.comment ?? ""
                if(orderWithAmout?.deliveryCost != nil){
                    //copiedState.deliveryCost = String(orderWithAmout!.deliveryCost) + Strings.CURRENCY
                }
                copiedState.isDelivery = orderWithAmout?.isDelivery ?? false
            }
        }
    }

}
