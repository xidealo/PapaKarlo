//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI
import shared

struct CreateOrderView: View {
    
    @StateObject private var viewModel = CreateOrderHolder()
    @State var showCreatedAddress:Bool = false
    @State var showAddressError:Bool = false
    @State var showCommonError:Bool = false
    @State var goToProfile:Bool = false
    @State var goToUserAddress:Bool = false
    @State var goToCafeAddress:Bool = false
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing: 0){
            ToolbarView(
                title: Strings.TITLE_CREATION_ORDER,
                cost: "",
                count: "",
                isCartVisible: false,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            NavigationLink(
                destination:UserAddressListView(isClickable: true),
                isActive: $goToUserAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination:CafeAddressListView(isClickable: true),
                isActive: $goToCafeAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination:ContainerView(selection: 2, showOrderCreated:true),
                isActive: $goToProfile
            ){
                EmptyView()
            }
            
            if(viewModel.creationOrderViewState.isLoading){
                LoadingView()
            }else{
                CreateOrderSuccessView(
                    viewModel: viewModel,
                    showCreatedAddress: $showCreatedAddress,
                    showAddressError: $showAddressError,
                    showCommonError: $showCommonError,
                    goToProfile:$goToProfile,
                    goToUserAddress:$goToUserAddress,
                    goToCafeAddress:$goToCafeAddress
                )
            }
        }
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.kmmViewModel.update()
        }
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
    
    @ObservedObject var viewModel:CreateOrderHolder
    @State var addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
    @Binding var showCreatedAddress:Bool
    @Binding var showAddressError:Bool
    @Binding var showCommonError:Bool
    @Binding var goToProfile:Bool
    @Binding var goToUserAddress:Bool
    @Binding var goToCafeAddress:Bool
    @State var isDelivery = true
    @State var comment = ""
    
    var body: some View{
        VStack(spacing:0){
            Switcher(
                leftTitle: Strings.MSG_CREATION_ORDER_DELIVERY,
                rightTitle: Strings.MSG_CREATION_ORDER_PICKUP,
                isLeftSelected: $isDelivery
            ){ isDelivery in
                if(isDelivery){
                    viewModel.kmmViewModel.onSwitcherPositionChanged(position: 0)
                }else{
                    viewModel.kmmViewModel.onSwitcherPositionChanged(position: 1)
                }
            }
            .padding(.top, Diems.MEDIUM_PADDING)
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            
            if(viewModel.creationOrderViewState.isDelivery){
                if viewModel.creationOrderViewState.deliveryAddress == nil{
                    NavigationCardView(
                        icon: nil,
                        label: addressLable,
                        destination: CreateAddressView(show: $showCreatedAddress)
                    )
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }else{
                    ActionTextCardView(
                        placeHolder: addressLable,
                        text: viewModel.getUserAddressList()
                    ){
                        viewModel.goToAddress()
                    }
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }
            }else{
                ActionTextCardView(
                    placeHolder: addressLable,
                    text: "\(viewModel.creationOrderViewState.pickupAddress ?? "")"
                ){
                    viewModel.goToAddress()
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)           
            }
            
            EditTextView(
                hint: Strings.HINT_CREATE_COMMENT_COMMENT,
                text: $comment,
                limit: 255,
                hasError: .constant(false)
            )
            .padding(.top, Diems.SMALL_PADDING)
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            
            //            Toggle("Как можно скорее", isOn: (viewModel.creationOrderViewState.deferredTime is TimeUIASAP))
            //                .toggleStyle(.automatic)
            //                .padding(.top, Diems.SMALL_PADDING)
            //                .padding(.horizontal, Diems.MEDIUM_PADDING)
            //
            //            if(!viewModel.creationOrderViewState.notNeedDeferredTime){
            //                if(viewModel.creationOrderViewState.isDelivery){
            //                    DatePicker("Время доставки", selection: $viewModel.creationOrderViewState.deferredTime, in: (Date.now + 60 * 60)..., displayedComponents: .hourAndMinute)
            //                        .padding(.top, Diems.SMALL_PADDING)
            //                        .padding(.horizontal, Diems.MEDIUM_PADDING)
            //                }else{
            //                    DatePicker("Время самовывоза", selection: $viewModel.creationOrderViewState.deferredTime, in: (Date.now + 60 * 60)..., displayedComponents: .hourAndMinute)
            //                        .padding(.top, Diems.SMALL_PADDING)
            //                        .padding(.horizontal, Diems.MEDIUM_PADDING)
            //                }
            //            }
            
            
            Spacer()
            
            LinearGradient(
                gradient: Gradient(colors: [.white.opacity(0.1), .white]), startPoint: .top, endPoint: .bottom
            )
            .frame(height:20)
            
            VStack(spacing:0){
                HStack(spacing:0){
                    Text(Strings.MSG_CREATION_ORDER_RESULT)
                        .foregroundColor(Color("onSurface"))
                    Spacer()
                    Text("\(viewModel.creationOrderViewState.totalCost ?? 0)")
                        .foregroundColor(Color("onSurface"))
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                
                if(viewModel.creationOrderViewState.isDelivery){
                    HStack(spacing:0){
                        Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                            .foregroundColor(Color("onSurface"))
                        Spacer()
                        Text("\(viewModel.creationOrderViewState.deliveryCost ?? 0)")
                            .foregroundColor(Color("onSurface"))
                    }
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }
                HStack(spacing:0){
                    BoldText(text:Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                    Spacer()
                    BoldText(text:"\(viewModel.creationOrderViewState.finalCost ?? 0)")
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
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
                )
                .padding(.top, Diems.MEDIUM_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(Color("surface"))
            .onReceive(viewModel.$creationOrderViewState, perform: { creationOrderViewState in
                if(creationOrderViewState.isDelivery){
                    addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
                }else{
                    addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_CAFE
                }
                //                print("LSIT \(creationOrderViewState.actionList)")
                //
                //                creationOrderViewState.actionList.forEach { action in
                //                    switch(action){
                //                    case CreateOrderAction.showAddressError : showAddressError = true
                //                    case CreateOrderAction.showCommonError : showCommonError = true
                //                    case CreateOrderAction.goToProfile : goToProfile = true
                //                    case CreateOrderAction.goToCafeAddressList : goToCafeAddress = true
                //                    case CreateOrderAction.goToUserAddressList : goToUserAddress = true
                //                    }
                //                }
                //                print("PROFILE \(goToProfile)")
                //
                //                if !creationOrderViewState.actionList.isEmpty{
                //                    viewModel.clearActions()
                //                }
            })
        }
    }
}
