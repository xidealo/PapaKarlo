//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI

struct CreateOrderView: View {
    
    @ObservedObject private var viewModel = CreateOrderViewModel()
    
    var body: some View {
        VStack(spacing: 0){
            ToolbarView(title: Strings.TITLE_CREATION_ORDER, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            if(viewModel.creationOrderViewState.isLoading){
                LoadingView()
            }else{
                CreateOrderSuccessView(viewModel: viewModel)
            }
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .navigationBarHidden(true)
    }
}

struct CreateOrderSuccessView:View {
    
    @ObservedObject var viewModel:CreateOrderViewModel
    
    var body: some View{
        if(viewModel.creationOrderViewState.isGoProfile){
            NavigationLink(destination:ProfileView(), isActive: .constant(true)){}
        }else{
            VStack{
                Switcher(leftTitle: Strings.MSG_CREATION_ORDER_DELIVERY, rightTitle: Strings.MSG_CREATION_ORDER_PICKUP, isLeftSelected:  $viewModel.creationOrderViewState.isDelivery){ isDelivery in
                    viewModel.getAddressList(isDelivery: isDelivery)
                }
                
                if viewModel.creationOrderViewState.address == nil{
                    NavigationCardView(icon: nil, label: Strings.HINT_CREATION_ORDER_ADDRESS, destination: CreateAddressView())
                }else{
                    NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_ADDRESS, text: viewModel.creationOrderViewState.address!, destination:UserAddressListView())
                }
                
                EditTextView(hint: Strings.HINT_CREATE_COMMENT_COMMENT, text:$viewModel.creationOrderViewState.comment, limit: 255)
                
//                    if(viewModel.creationOrderViewState.isDelivery){
//                        NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_DEFERRED_DELIVERY, text: viewModel.creationOrderViewState.deferredTime, destination:UserAddressListView())
//                    }else{
//                        NavigationTextCard(placeHolder: Strings.HINT_CREATION_ORDER_DEFERRED_PICKUP, text: viewModel.creationOrderViewState.deferredTime, destination:UserAddressListView())
//                    }
            }.padding(Diems.MEDIUM_PADDING)
            
            Spacer()
            
            LinearGradient(gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom).frame(height:20)
            VStack{
                HStack{
                    Text(Strings.MSG_CREATION_ORDER_RESULT)
                    Spacer()
                    Text(viewModel.creationOrderViewState.totalCost)
                }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                
                if(viewModel.creationOrderViewState.isDelivery){
                    HStack{
                        Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                        Spacer()
                        Text(viewModel.creationOrderViewState.deliveryCost)
                    }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                    HStack{
                        BoldText(text:Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                        Spacer()
                        BoldText(text:viewModel.creationOrderViewState.amountToPayWithDeliveryCost)
                    }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                }else{
                    HStack{
                        BoldText(text:Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                        Spacer()
                        BoldText(text:viewModel.creationOrderViewState.amountToPay)
                    }.padding(.top, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                    
                }
                Button(
                    action: {
                        viewModel.createOrder()
                    }, label: {
                        Text(Strings.ACTION_CART_PRODUCT_CREATE_ORDER).frame(maxWidth: .infinity)
                            .padding()
                            .foregroundColor(Color("surface"))
                            .background(Color("primary"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                    }
                ).padding(Diems.MEDIUM_PADDING)
            }.background(Color("surface"))
        }
    }
    
}


struct CreateOrderView_Previews: PreviewProvider {
    static var previews: some View {
        CreateOrderView()
    }
}
