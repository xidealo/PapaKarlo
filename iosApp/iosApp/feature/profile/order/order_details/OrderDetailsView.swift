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
        orderDetailsList: [],
        orderInfo: nil,
        totalCost: nil,
        deliveryCost: nil,
        finalCost: nil,
        isLoading: true
    )
    
    var viewModel = OrderDetailsViewModel(
        observeOrderUseCase: iosComponent.provideObserveOrderUseCase(),
        timeMapper: iosComponent.provideTimeMapper()
    )
    
    @State var listener: Closeable? = nil

    @State var orderUuid:String
    
    var body: some View {
        VStack(spacing:0){
            if(orderDetailsState.isLoading){
                LoadingView()
            }else{
                ToolbarView(
                    title: orderDetailsState.orderInfo?.code ?? "",
                    back: {
                        self.mode.wrappedValue.dismiss()
                    }
                )

                ZStack(alignment: .bottom){
                    ScrollView {
                        LazyVStack(spacing: 0){
                            OrderStatusBar(
                                orderStatus: orderDetailsState.orderInfo?.status ??  OrderStatus.notAccepted,
                                orderStatusName: orderDetailsState.orderInfo?.status.name ?? ""
                            )
                                .padding(.top, Diems.MEDIUM_PADDING)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)

                            if(orderDetailsState.orderInfo != nil){
                                OrderDetailsTextView(orderDetails: orderDetailsState.orderInfo!)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.MEDIUM_PADDING)
                                    .padding(.bottom, Diems.SMALL_PADDING)
                            }

                            VStack(spacing: 0){
                                ForEach(orderDetailsState.orderDetailsList){ orderProductItem in
                                    OrderProductItemView(orderProductItem: orderProductItem)
                                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                                        .padding(.top, Diems.SMALL_PADDING)
                                }
                            }
                            .padding(.bottom, Diems.MEDIUM_PADDING)
                        }
                    }

                    LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom)
                        .frame(height:20)

                }
                VStack(spacing:0){
                    if(orderDetailsState.deliveryCost != nil){
                        HStack(spacing:0){
                            Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                            Spacer()
                            Text(orderDetailsState.deliveryCost ?? "0")
                        }
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                        .padding(.top, Diems.SMALL_PADDING)
                    }

                    HStack(spacing:0){
                        BoldText(text: Strings.MSG_CART_PRODUCT_RESULT)
                        Spacer()
                        BoldText(text: orderDetailsState?.finalCost ?? "")
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.bottom, Diems.MEDIUM_PADDING)
                }.background(Color("surface"))
            }
        }
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.loadOrder(orderUuid: orderUuid)
            
            listener = viewModel.orderState.watch { orderDetailsStateVM in
                if(orderListVM != nil ){
                    orderDetailsState = orderDetailsStateVM!
                }
                // work with actions
            }
        }
        .onDisappear(){
            //viewModel.unsubscribeFromOrders()
            
            //viewModel.stopObserveOrders()
            listener?.close()
            listener = nil
        }
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
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                    .foregroundColor(Color("onSurface"))
                
                HStack(spacing:0){
                    if orderProductItem.oldPrice != nil {
                        StrikeText(text: orderProductItem.oldPrice!)
                    }
                    Text(orderProductItem.newPrice)
                        .foregroundColor(Color("onSurface"))
                    Text(orderProductItem.count)
                        .foregroundColor(Color("onSurface"))
                    
                    HStack(spacing:0){
                        if orderProductItem.oldCost != nil {
                            StrikeText(text: orderProductItem.oldCost!)
                        }
                        Text(orderProductItem.newCost)
                            .foregroundColor(Color("onSurface"))
                    }.frame(maxWidth:.infinity, alignment: .topTrailing)
                        .padding(.trailing, Diems.SMALL_PADDING)
                    
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
                .padding(.leading, Diems.SMALL_PADDING)
            
        }.frame(maxWidth:.infinity, alignment: .topLeading)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
struct OrderDetailsTextView: View {
    
    let orderDetails:OrderDetailsState.OrderInfo
    
    var body: some View {
        VStack(spacing:0){
//            HStack(spacing:0){
//                VStack(spacing:0){
//                    PlaceholderText(text: "Время заказа")
//                        .frame(maxWidth: .infinity, alignment: .leading)
//                    Text(orderDetails.dateTime)
//                        .frame(maxWidth: .infinity, alignment: .leading)
//                }
//                .padding(.top, Diems.HALF_SMALL_PADDING)
//
//                if(orderDetails.deferredTime != nil){
//                    VStack(spacing:0){
//                        PlaceholderText(text: orderDetails.deferredTimeHintString)
//                            .frame(maxWidth: .infinity, alignment: .leading)
//                        Text(orderDetails.deferredTime!)
//                            .frame(maxWidth: .infinity, alignment: .leading)
//                    }
//                    .padding(.top, Diems.HALF_SMALL_PADDING)
//                }
//            }
//            VStack(spacing:0){
//                PlaceholderText(text: "Способ получения")
//                    .frame(maxWidth: .infinity, alignment: .leading)
//                Text(orderDetails.pickupMethod)
//                    .frame(maxWidth: .infinity, alignment: .leading)
//            }
//            .padding(.top, Diems.HALF_SMALL_PADDING)
//
//            VStack(spacing:0){
//                PlaceholderText(text: "Адрес")
//                    .frame(maxWidth: .infinity, alignment: .leading)
//                Text(orderDetails.address)
//                    .frame(maxWidth: .infinity, alignment: .leading)
//            }
//            .padding(.top, Diems.HALF_SMALL_PADDING)
//            if(orderDetails.comment != nil && orderDetails.comment != ""){
//                VStack(spacing:0){
//                    PlaceholderText(text: "Комментарий")
//                        .frame(maxWidth: .infinity, alignment: .leading)
//                    Text(orderDetails.comment ?? "")
//                        .frame(maxWidth: .infinity, alignment: .leading)
//                }.padding(.top, Diems.HALF_SMALL_PADDING)
//            }
        }.frame(maxWidth: .infinity, alignment: .leading)
            .padding(Diems.MEDIUM_PADDING)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
