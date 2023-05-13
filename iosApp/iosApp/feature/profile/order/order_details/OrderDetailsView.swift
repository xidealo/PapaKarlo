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
    
    @State var orderDetailsState = OrderDetailsState(
        orderProductItemList: [],
        orderInfo: nil,
        totalCost: nil,
        deliveryCost: nil,
        finalCost: nil,
        isLoading: true
    )
    
    var viewModel = OrderDetailsViewModel(
        observeOrderUseCase: iosComponent.provideObserveOrderUseCase(),
        timeMapper: iosComponent.provideTimeMapper(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase()
    )
    
    @State var listener: Closeable? = nil
    
    @State var orderUuid:String
    
    @Environment(\.scenePhase) var scenePhase
    
    var body: some View {
        VStack(spacing:0){
            if(orderDetailsState.isLoading){
                LoadingView()
            }else{
                ToolbarView(
                    title: LocalizedStringKey(orderDetailsState.orderInfo?.code ?? ""),
                    back: {
                        self.mode.wrappedValue.dismiss()
                    }
                )
                
                ScrollView {
                    LazyVStack(spacing: 0){
                        OrderStatusBar(
                            orderStatus: orderDetailsState.orderInfo?.status ??  OrderStatus.notAccepted,
                            orderStatusName: orderDetailsState.orderInfo?.status.name ?? ""
                        )
                        .padding(.top, Diems.MEDIUM_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                        
                        if let orderInfo = orderDetailsState.orderInfo{
                            OrderDetailsTextView(orderDetails: orderInfo)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.top, Diems.MEDIUM_PADDING)
                                .padding(.bottom, Diems.SMALL_PADDING)
                        }
                        
                        VStack(spacing: 0){
                            ForEach(orderDetailsState.orderProductItemList.map({ order in
                                OrderProductItem(
                                    id: order.uuid,
                                    name: order.name,
                                    newPrice: order.newPrice,
                                    oldPrice: order.oldPrice,
                                    newCost: order.newCost,
                                    oldCost: order.oldCost,
                                    photoLink: order.photoLink,
                                    count: String(order.count)
                                )
                            })){ orderProductItem in
                                OrderProductItemView(orderProductItem: orderProductItem)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                            }
                        }
                        .padding(.bottom, Diems.MEDIUM_PADDING)
                    }
                }
                
                
                VStack(spacing:0){
                    if(orderDetailsState.deliveryCost != nil){
                        HStack(spacing:0){
                            Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                                .bodyMedium()
                            Spacer()
                            Text((orderDetailsState.deliveryCost ?? "0") + Strings.CURRENCY)
                                .bodyMedium()
                        }
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                        .padding(.top, Diems.SMALL_PADDING)
                    }
                    
                    HStack(spacing:0){
                        Text(Strings.MSG_CART_PRODUCT_RESULT)
                            .bodyMedium(weight: .bold)
                        Spacer()
                        if orderDetailsState.totalCost != nil {
                            Text((orderDetailsState.totalCost ?? "") + Strings.CURRENCY)
                                .strikethrough()
                                .bodyMedium(weight: .bold)
                                .foregroundColor(AppColor.onSurfaceVariant)
                                .padding(.trailing, Diems.SMALL_PADDING)
                        }
                        Text((orderDetailsState.finalCost ?? "") + Strings.CURRENCY)
                            .bodyMedium(weight: .bold)
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.bottom, Diems.MEDIUM_PADDING)
                }.background(AppColor.surface)
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .onChange(of: scenePhase) { newPhase in
            if newPhase == .active {
                subscribe()
            } else if newPhase == .inactive {
                unsubscribe()
            } else if newPhase == .background {
                unsubscribe()
            }
        }
    }
    
    func subscribe(){
        viewModel.loadOrder(orderUuid: orderUuid)
        listener = viewModel.orderState.watch { orderDetailsStateVM in
            if(orderDetailsStateVM != nil ){
                orderDetailsState = orderDetailsStateVM!
            }
        }
    }
    
    func unsubscribe(){
        viewModel.stopObserveOrders()
        listener?.close()
        listener = nil
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

                Spacer()
                
                HStack(spacing:0){
                    if let oldPrice = orderProductItem.oldPrice{
                        Text(oldPrice + Strings.CURRENCY)
                            .strikethrough()
                            .bodySmall()
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .padding(.trailing, Diems.SMALL_PADDING)
                    }
                    
                    Text(orderProductItem.newPrice + Strings.CURRENCY)
                        .bodySmall(weight: .bold)
                        .foregroundColor(AppColor.onSurface)
                    
                    Text(" x ")
                        .bodySmall()
                        .foregroundColor(AppColor.onSurface)
                    
                    Text(orderProductItem.count)
                        .bodySmall()
                        .foregroundColor(AppColor.onSurface)
                    
                    HStack(spacing:0){
                        if let oldCost = orderProductItem.oldCost{
                            Text(oldCost + Strings.CURRENCY)
                                .strikethrough()
                                .bodySmall()
                                .foregroundColor(AppColor.onSurfaceVariant)
                                .padding(.trailing, Diems.SMALL_PADDING)
                        }
                        
                        Text(orderProductItem.newCost + Strings.CURRENCY)
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
    
    let orderDetails:OrderDetailsState.OrderInfo
    
    var body: some View {
        VStack(spacing:0){
            HStack(spacing:0){
                VStack(spacing:0){
                    Text("Время заказа")
                        .labelSmall(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Text(dateUtil.getDateTimeString(dateTime: orderDetails.dateTime))
                        .bodyMedium()
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                
                if(orderDetails.deferredTime != nil && orderDetails.deferredTime is TimeUITime){
                    VStack(spacing:0){
                        Text("Время доставки")
                            .labelSmall(weight: .medium)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        Text(dateUtil.getTimeString(time: orderDetails.deferredTime as! TimeUITime))
                            .bodyMedium()
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                    .padding(.top, Diems.HALF_SMALL_PADDING)
                }
            }
            VStack(spacing:0){
                Text("Способ получения")
                    .labelSmall(weight: .medium)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                if(orderDetails.isDelivery){
                    Text("Доставка")
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }else{
                    Text("Самовывоз")
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            
            VStack(spacing:0){
                Text("Адрес")
                    .labelSmall(weight: .medium)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(orderDetails.address.getAddress())
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
