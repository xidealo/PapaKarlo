//
//  OrderDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI
import Kingfisher
import shared

struct OrderDetailsView: View {
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var viewModel = OrderDetailsViewModel(
        observeOrderUseCase: iosComponent.provideObserveOrderUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase()
    )
    
    @State var orderDetailsViewState = OrderDetailsViewState(
        orderUuid: "",
        orderProductItemList: [],
        deliveryCost: nil,
        newTotalCost: "",
        state: OrderDetailsDataState.ScreenState.loading,
        code: "",
        orderInfo: nil,
        discount: nil
    )
    
    //Listeners
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    //-----
    
    @State var orderUuid:String
    
    @Environment(\.scenePhase) var scenePhase
    
    var body: some View {
        VStack(spacing:0){
            if(orderDetailsViewState.state == OrderDetailsDataState.ScreenState.loading){
                LoadingView()
            }else{
                ToolbarView(
                    title: LocalizedStringKey(orderDetailsViewState.code),
                    back: {
                        viewModel.onAction(action: OrderDetailsActionBack())
                    }
                )
                
                ScrollView {
                    LazyVStack(spacing: 0){
                        OrderStatusBar(
                            orderStatus: orderDetailsViewState.orderInfo?.status ??  OrderStatus.notAccepted,
                            orderStatusName: orderDetailsViewState.orderInfo?.status.name ?? ""
                        )
                        
                        if let orderInfo = orderDetailsViewState.orderInfo{
                            OrderDetailsTextView(orderDetails: orderInfo)
                                .padding(.top, 8)
                                .padding(.bottom, Diems.SMALL_PADDING)
                        }
                        
                        VStack(spacing: 0){
                            ForEach(orderDetailsViewState.orderProductItemList){ orderProductItem in
                                OrderProductItemView(orderProductItem: orderProductItem)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                                
                                if(!orderProductItem.isLast){
                                    Divider()
                                        .padding(.top, 8)
                                        .padding(.horizontal, 16)
                                }
                                
                            }
                        }
                        .padding(.bottom, Diems.MEDIUM_PADDING)
                    }
                }
                
                VStack(spacing:0){
                    if let discount = orderDetailsViewState.discount{
                        HStack(spacing:0){
                            Text("title_order_details_discount")
                                .bodyMedium()
                                .foregroundColor(AppColor.onSurface)
                            
                            Spacer()
                            
                            DiscountCard(text:discount)
                        }.padding(.top, 8)
                            .padding(.horizontal, 16)
                    }
                    
                    
                    if let deliveryCost = orderDetailsViewState.deliveryCost{
                        HStack(spacing:0){
                            Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                                .bodyMedium()
                            Spacer()
                            Text(deliveryCost)
                                .bodyMedium()
                        }
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                        .padding(.top, Diems.SMALL_PADDING)
                    }
                    
                    HStack(spacing:0){
                        Text("title_order_details_sum")
                            .bodyMedium(weight: .bold)
                        Spacer()
                        
                        Text((orderDetailsViewState.newTotalCost))
                            .bodyMedium(weight: .bold)
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.bottom, Diems.MEDIUM_PADDING)
                }.background(AppColor.surface)
            }
        }
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .onChange(of: scenePhase) { newPhase in
            if newPhase == .active {
                subscribe()
                eventsSubscribe()
            } else if newPhase == .inactive {
                unsubscribe()
            } else if newPhase == .background {
                unsubscribe()
            }
        }
    }
    
    func subscribe(){
        viewModel.onAction(
            action: OrderDetailsActionStartObserve(orderUuid: orderUuid)
        )
        
        viewModel.dataState.watch { orderDetailsVM in
            if let orderDetailsStateVM =  orderDetailsVM {
                if let orderInfo = orderDetailsStateVM.orderDetailsData.orderInfo {
                    orderDetailsViewState = OrderDetailsViewState(
                        orderUuid: orderDetailsStateVM.orderUuid,
                        orderProductItemList: orderDetailsStateVM.orderDetailsData.orderProductItemList.map({ productItem in
                            OrderProductItem(
                                id: productItem.uuid,
                                name: productItem.name,
                                newPrice: productItem.newCost,
                                newCost: productItem.newCost,
                                photoLink: productItem.photoLink,
                                count: productItem.count,
                                additions: productItem.additions.map({ orderAddition in
                                    orderAddition.name
                                }).joined(separator: " • "),
                                isLast: productItem.isLast
                            )
                        }),
                        deliveryCost: orderDetailsStateVM.orderDetailsData.deliveryCost,
                        newTotalCost: orderDetailsStateVM.orderDetailsData.newTotalCost,
                        state: orderDetailsStateVM.screenState,
                        code: orderDetailsStateVM.orderDetailsData.orderInfo?.code ?? "",
                        orderInfo: OrderInfo(
                            status: orderInfo.status,
                            statusName: OrderChip.getStatusName(status: orderInfo.status),
                            dateTime: dateUtil.getDateTimeString(dateTime: orderInfo.dateTime),
                            deferredTime:  getDefferedTime(defferedTime: orderInfo.deferredTime),
                            address: orderInfo.address.getAddress(),
                            comment: orderInfo.comment,
                            pickupMethod: getPickupMethod(isDelivery: orderInfo.isDelivery),
                            deferredTimeHint: getDeferredTimeHint(isDelivery: orderInfo.isDelivery),
                            paymentMethod: orderInfo.paymentMethod?.getPaymentMethod()
                        ),
                        discount: orderDetailsStateVM.orderDetailsData.discount
                    )
                }
            }
        }
    }
    
    
    func getDeferredTimeHint(isDelivery:Bool) -> String {
        if(isDelivery) {
            return "Время доставки"
        } else {
            return "Время самовывоза"
        }
    }
    
    func getDefferedTime(defferedTime:Time?) -> String? {
        if let time = defferedTime{
            return dateUtil.getTimeString(time: time)
        }
        return nil
    }
    
    
    func getPickupMethod(isDelivery:Bool) -> String {
        if(isDelivery) {
            return "Доставка"
        } else {
            return "Самовывоз"
        }
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let orderDetailsStateEvents = events as? [OrderDetailsEvent] ?? []
                
                orderDetailsStateEvents.forEach { event in
                    switch(event){
                    case is OrderDetailsEventBack : self.mode.wrappedValue.dismiss()
                    default:
                        print("def")
                    }
                }
                
                if !orderDetailsStateEvents.isEmpty {
                    viewModel.consumeEvents(events: orderDetailsStateEvents)
                }
            }
        })
    }
    
    func unsubscribe(){
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct OrderProductItemView :View {
    var orderProductItem:OrderProductItem
    
    var body: some View {
        HStack(spacing: 0){
            KFImage(URL(string: orderProductItem.photoLink))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack(spacing:0){
                Text(orderProductItem.name)
                    .titleSmall(weight: .bold)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .foregroundColor(AppColor.onSurface)
                    .padding(.top, 8)
                
                Text(orderProductItem.additions)
                    .bodySmall(weight: .regular)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .padding(.top, 4)
                
                Spacer()
                
                HStack(spacing:0){
                    
                    Text(orderProductItem.newPrice)
                        .bodySmall(weight: .bold)
                        .foregroundColor(AppColor.onSurface)
                    
                    Text(" × ")
                        .bodySmall()
                        .foregroundColor(AppColor.onSurface)
                    
                    Text(orderProductItem.count)
                        .bodySmall()
                        .foregroundColor(AppColor.onSurface)
                    
                    HStack(spacing:0){
                        Text(orderProductItem.newCost)
                            .bodySmall(weight: .bold)
                            .foregroundColor(AppColor.onSurface)
                    }
                    .frame(maxWidth:.infinity, alignment: .topTrailing)
                    .padding(.trailing, Diems.SMALL_PADDING)
                }
                .padding(.bottom, 8)
            }
            .frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            .padding(.leading, Diems.SMALL_PADDING)
        }.frame(maxWidth:.infinity, alignment: .topLeading)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
struct OrderDetailsTextView: View {
    
    let orderDetails: OrderInfo
    
    var body: some View {
        VStack(spacing:0){
            HStack(spacing:0){
                VStack(spacing:0){
                    Text("Время заказа")
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Text(orderDetails.dateTime)
                        .bodyMedium()
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                
                if(orderDetails.deferredTime != nil){
                    VStack(spacing:0){
                        Text(orderDetails.deferredTimeHint)
                            .labelSmall(weight: .medium)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        if let time = orderDetails.deferredTime{
                            Text(time)
                                .bodyMedium()
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }
                        
                    }
                }
            }
            
            HStack(spacing:0 ){
                VStack(spacing:0){
                    Text("Способ получения")
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Text(orderDetails.pickupMethod)
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                
                if let paymentMethod = orderDetails.paymentMethod {
                    VStack(spacing:0){
                        Text("selectable_payment_method")
                            .labelSmall(weight: .medium)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        Text(paymentMethod)
                            .bodyMedium()
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                }
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            
            VStack(spacing:0){
                Text("Адрес")
                    .labelSmall(weight: .medium)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(orderDetails.address)
                    .bodyMedium()
                    .frame(maxWidth: .infinity, alignment: .leading)
                
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            if(orderDetails.comment != nil && orderDetails.comment != ""){
                VStack(spacing:0){
                    Text("Комментарий")
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Text(orderDetails.comment ?? "")
                        .bodyMedium()
                        .frame(maxWidth: .infinity, alignment: .leading)
                }.padding(.top, Diems.HALF_SMALL_PADDING)
            }
        }.frame(maxWidth: .infinity, alignment: .leading)
            .padding(Diems.MEDIUM_PADDING)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
