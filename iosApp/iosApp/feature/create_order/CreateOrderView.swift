//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI

struct CreateOrderView: View {
    
    @ObservedObject private var viewModel = CreateOrderViewModel()
    @State var showCreatedAddress:Bool = false
    @State var showAddressError:Bool = false
    @State var showCommonError:Bool = false
    
    var body: some View {
        VStack(spacing: 0){
            ToolbarView(title: Strings.TITLE_CREATION_ORDER, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            if(viewModel.creationOrderViewState.createOrderState == CreateOrderState.loading){
                LoadingView()
            }else{
                CreateOrderSuccessView(viewModel: viewModel, showCreatedAddress: $showCreatedAddress, showAddressError: $showAddressError, showCommonError: $showCommonError)
            }
        }.onAppear(){
            viewModel.loadData()
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .navigationBarHidden(true)
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес добавлен"),
                show: $showCreatedAddress,
                backgroundColor:Color("primary"),
                foregaroundColor: Color("onPrimary")),
            show: $showCreatedAddress
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Не указан адрес"),
                show: $showAddressError,
                backgroundColor:Color("errorColor"),
                foregaroundColor: Color("onPrimary")),
            show: $showAddressError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Что-то пошло не так")
                , show: $showCommonError,
                backgroundColor:Color("errorColor"),
                foregaroundColor: Color("onPrimary")),
            show: $showCommonError
        )
    }
}

struct CreateOrderSuccessView:View {
    
    @ObservedObject var viewModel:CreateOrderViewModel
    @State var addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
    @Binding var showCreatedAddress:Bool
    @Binding var showAddressError:Bool
    @Binding var showCommonError:Bool
    
    var body: some View{
        switch(viewModel.creationOrderViewState.createOrderState){
        case CreateOrderState.goToProfile: NavigationLink(
            destination:ProfileView(showOrderCreated: true),
            isActive: .constant(true)
        ){
            EmptyView()
        }
        case CreateOrderState.goToCafeAddressList: NavigationLink(
            destination:CafeAddressListView(isClickable: true),
            isActive: .constant(true)
        ){
            EmptyView()
        }
        case CreateOrderState.goToUserAddressList: NavigationLink(
            destination:UserAddressListView(isClickable: true),
            isActive: .constant(true)
        ){
            EmptyView()
        }
        default :  VStack{
            Switcher(leftTitle: Strings.MSG_CREATION_ORDER_DELIVERY, rightTitle: Strings.MSG_CREATION_ORDER_PICKUP, isLeftSelected:  $viewModel.creationOrderViewState.isDelivery){ isDelivery in
                viewModel.getAddressList(isDelivery: isDelivery)
            }
            
            if viewModel.creationOrderViewState.address == nil{
                NavigationCardView(icon: nil, label: addressLable, destination: CreateAddressView(show: $showCreatedAddress))
            }else{
                ActionTextCardView(placeHolder: addressLable, text: viewModel.creationOrderViewState.address!){
                    viewModel.goToAddress()
                }
            }
            
            EditTextView(
                hint: Strings.HINT_CREATE_COMMENT_COMMENT,
                text:$viewModel.creationOrderViewState.comment,
                limit: 255,
                hasError: .constant(false)
            )
            
            Toggle("Как можно скорее", isOn: $viewModel.creationOrderViewState.notNeedDeferredTime)
                .toggleStyle(.automatic)
            
            if(!viewModel.creationOrderViewState.notNeedDeferredTime){
                if(viewModel.creationOrderViewState.isDelivery){
                    DatePicker("Время доставки", selection: $viewModel.creationOrderViewState.deferredTime, in: (Date.now + 60 * 60)..., displayedComponents: .hourAndMinute)
                }else{
                    DatePicker("Время самовывоза", selection: $viewModel.creationOrderViewState.deferredTime, in: (Date.now + 60 * 60)..., displayedComponents: .hourAndMinute)
                }
            }
            
        }.padding(Diems.MEDIUM_PADDING)
            
            Spacer()
            
            LinearGradient(
                gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom
            )
            .frame(height:20)
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
                .onReceive(viewModel.$creationOrderViewState, perform: { creationOrderViewState in
                    if(creationOrderViewState.isDelivery){
                        addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
                    }else{
                        addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_CAFE
                    }
                    print(creationOrderViewState.createOrderState == CreateOrderState.addressError)
                    showCommonError = creationOrderViewState.createOrderState == CreateOrderState.commonError
                    
                    creationOrderViewState.actionList.forEach { action in
                        switch(action){
                        case CreateOrderAction.showAddressError : showAddressError = true
                        case CreateOrderAction.showCommonError : showCommonError = true
                        }
                    }
                    
                    if !creationOrderViewState.actionList.isEmpty{
                        viewModel.clearActions()
                    }
                    
                })
        
        }
    }
}

struct CreateOrderView_Previews: PreviewProvider {
    static var previews: some View {
        CreateOrderView()
    }
}
