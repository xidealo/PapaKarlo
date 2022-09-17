//
//  OrderDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI
import Kingfisher

struct OrderDetailsView: View {
    
    @ObservedObject private var viewModel : OrderDetailsViewModel
    
    init(orderUuid:String){
        viewModel = OrderDetailsViewModel(uuid: orderUuid)
    }
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(title: viewModel.orderDetailsViewState.code, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            ZStack(alignment: .bottom){
                LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom).frame(height:20)
                
                ScrollView {
                    LazyVStack(spacing: 0){
                        OrderStatusBar(orderStatus: viewModel.orderDetailsViewState.status, orderStatusName: viewModel.orderDetailsViewState.statusName)
                            .padding(.top, Diems.MEDIUM_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        
                        OrderDetailsTextView(orderDetails: viewModel.orderDetailsViewState)      .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.top, Diems.MEDIUM_PADDING)

                        ForEach(viewModel.orderDetailsViewState.orderProductList){ orderProductItem in
                            OrderProductItemView(orderProductItem: orderProductItem)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.vertical, Diems.SMALL_PADDING)
                        }.padding(.vertical, Diems.SMALL_PADDING)

                    }
                }
            }
            VStack{
                if(viewModel.orderDetailsViewState.deliveryCost != nil){
                    HStack{
                        Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                        Spacer()
                        Text(viewModel.orderDetailsViewState.deliveryCost ?? "0")
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.top, Diems.SMALL_PADDING)
                }
               
                HStack{
                    BoldText(text: Strings.MSG_CART_PRODUCT_RESULT)
                    Spacer()
                    BoldText(text: viewModel.orderDetailsViewState.newAmountToPay)
                }
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.top, Diems.SMALL_PADDING)
            }.background(Color("surface"))
            
            
        }.background(Color("background"))
            .hiddenNavigationBarStyle()
    }
}

struct OrderProductItemView :View {
    var orderProductItem:OrderProductItem
    
    var body: some View {
        HStack{
            KFImage(URL(string: orderProductItem.photoLink))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
            VStack{
                Text(orderProductItem.name)
                    .frame(maxWidth:.infinity, alignment: .topLeading)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
                    .foregroundColor(Color("onSurface"))
                
                HStack{
                    if orderProductItem.oldPrice != nil {
                        StrikeText(text: orderProductItem.oldPrice!)
                    }
                    Text(orderProductItem.newPrice)
                        .foregroundColor(Color("onSurface"))
                    Text(orderProductItem.count)
                        .foregroundColor(Color("onSurface"))
                    
                    HStack{
                        if orderProductItem.oldCost != nil {
                            StrikeText(text: orderProductItem.oldCost!)
                        }
                        Text(orderProductItem.newCost)
                            .foregroundColor(Color("onSurface"))
                    }.frame(maxWidth:.infinity, alignment: .topTrailing)
                        .padding(.trailing, Diems.SMALL_PADDING)
                    
                }
            }.frame(maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
            
        }.frame(maxWidth:.infinity, alignment: .topLeading)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
struct OrderDetailsTextView: View {
    
    let orderDetails:OrderDetailsViewState
    
    var body: some View {
        VStack{
            VStack{
                PlaceholderText(text: "Время заказа")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text(orderDetails.dateTime)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            HStack{
                VStack{
                    PlaceholderText(text: "Способ получения")
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text(orderDetails.pickupMethod)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .padding(.top, Diems.HALF_SMALL_PADDING)
                if(orderDetails.deferredTime != nil){
                    VStack{
                        PlaceholderText(text: orderDetails.deferredTimeHintString)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text(orderDetails.deferredTime!)
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                    .padding(.top, Diems.HALF_SMALL_PADDING)
                }
            }
            VStack{
                PlaceholderText(text: "Адрес")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text(orderDetails.address)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
            
            VStack{
                PlaceholderText(text: "Комментарий")
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text(orderDetails.comment ?? "")
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.top, Diems.HALF_SMALL_PADDING)
        }.frame(maxWidth: .infinity, alignment: .leading)
        .padding(Diems.MEDIUM_PADDING)
        .background(Color("surface"))
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct OrderDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        OrderDetailsView(orderUuid: "")
    }
}
