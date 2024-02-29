//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI
import shared
import Combine

struct CreateOrderView: View {
    
    @StateObject private var viewModel = CreateOrderHolder()
    @State var showCreatedAddress:Bool = false
    @State var showAddressError:Bool = false
    @State var showCommonError:Bool = false
    @State var showPaymentMethodError:Bool = false
    @State var goToUserAddress:Bool = false
    @State var goToCafeAddress:Bool = false
    @State var goToSelectPaymentMethod:Bool = false
    
    //for back after createOrder
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var addressList: [SelectableCafeAddressItem] = []
    @State var paymentList: [SelectablePaymentMethod] = []
    @State var selectedPaymentUuid:String? = nil
    
    var body: some View {
        VStack(spacing: 0){
            ToolbarView(
                title: "titleCreationOrder",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            NavigationLink(
                destination:UserAddressListView(
                    title: "title_delivery_addresses",
                    isClickable: true
                ),
                isActive: $goToUserAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination:CafeAddressListView(
                    isClickable: true,
                    _title: "title_pickup_addresses",
                    addressList: addressList
                ),
                isActive: $goToCafeAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination:SelectablePaymentListView(
                    paymentList: paymentList,
                    selectedPaymentUuid : $selectedPaymentUuid
                ),
                isActive: $goToSelectPaymentMethod
            ){
                EmptyView()
            }
            .onChange(of: $selectedPaymentUuid.wrappedValue, perform: { value in
                viewModel.kmmViewModel.onPaymentMethodChanged(paymentMethodUuid:selectedPaymentUuid ?? "")
            })
            
            if(viewModel.creationOrderViewState.isLoading){
                LoadingView()
            }else{
                CreateOrderSuccessView(
                    viewModel: viewModel,
                    showCreatedAddress: $showCreatedAddress,
                    showAddressError: $showAddressError,
                    showCommonError: $showCommonError,
                    showPaymentMethodError:$showPaymentMethodError,
                    goToUserAddress:$goToUserAddress,
                    goToCafeAddress:$goToCafeAddress,
                    goToSelectPaymentMethod : $goToSelectPaymentMethod,
                    isRootActive: $isRootActive,
                    selection: $selection,
                    showOrderCreated: $showOrderCreated,
                    addressList: $addressList,
                    paymentList: $paymentList
                )
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.update()
        }
        .onDisappear(){
            viewModel.removeListener()
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес добавлен"),
                show: $showCreatedAddress,
                backgroundColor:AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $showCreatedAddress
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Не указан адрес"),
                show: $showAddressError,
                backgroundColor:AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showAddressError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Что-то пошло не так")
                , show: $showCommonError,
                backgroundColor:AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showCommonError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Способ оплаты не выбран"),
                show: $showPaymentMethodError,
                backgroundColor:AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showPaymentMethodError
        )
        
    }
}

struct CreateOrderSuccessView: View {
    
    @ObservedObject var viewModel:CreateOrderHolder
    @State var addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
    @Binding var showCreatedAddress:Bool
    @Binding var showAddressError:Bool
    @Binding var showCommonError:Bool
    @Binding var showPaymentMethodError:Bool
    @Binding var goToUserAddress:Bool
    @Binding var goToCafeAddress:Bool
    @Binding var goToSelectPaymentMethod:Bool
    @State var isDelivery = true
    @State var comment = ""
    @State var faster = true
    @State var deferredTime: Foundation.Date = Foundation.Date()
    
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    @Binding var addressList: [SelectableCafeAddressItem]
    @Binding var paymentList: [SelectablePaymentMethod]
    
    let calendar = Calendar.current
    
    var body: some View{
        ZStack (alignment: .bottom){
            ScrollView{
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
                    
                    if(viewModel.creationOrderViewState.paymentMethod == nil){
                        ActionCardView(
                            icon: nil,
                            label: "Способ оплаты",
                            isSystemImageName: false,
                            isShowRightArrow: true
                        ){
                            viewModel.onPaymentMethodClick()
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }else{
                        ActionLocalizedTextCardView(
                            placeHolder: "selectable_payment_method",
                            text: viewModel.creationOrderViewState.paymentMethod!.name.getPaymentMethod()
                        ){
                            viewModel.onPaymentMethodClick()
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                    
                    EditTextView(
                        hint: Strings.HINT_CREATE_COMMENT_COMMENT,
                        text: $comment,
                        limit: 255,
                        errorMessage: .constant(nil),
                        textChanged: { str in
                            viewModel.kmmViewModel.onCommentChanged(comment: comment)
                        }
                    )
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    
                    Toggle(isOn: $faster.onChange({ faster in
                        if(faster) {
                            viewModel.kmmViewModel.onDeferredTimeSelected(deferredTime: nil)
                        }else{
                            let date =  Date.now + 60 * 60
                            
                            viewModel.kmmViewModel.onDeferredTimeSelected(
                                deferredTime: Time(
                                    hours: Int32(calendar.component(.hour, from: date)),
                                    minutes: Int32(calendar.component(.minute, from: date)
                                                  )
                                )
                            )
                        }
                    })){
                        Text("Как можно скорее")
                            .bodyLarge()
                    }
                    .toggleStyle(.automatic)
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    
                    if(!faster){
                        if(viewModel.creationOrderViewState.isDelivery){
                            DatePicker(
                                selection: $deferredTime.onChange(
                                    { date in
                                        viewModel.kmmViewModel.onDeferredTimeSelected(
                                            deferredTime: Time(
                                                hours: Int32(calendar.component(.hour, from: date)),
                                                minutes: Int32(calendar.component(.minute, from: date)
                                                              )
                                            )
                                        )
                                    }
                                ),
                                in: (Date.now + 60 * 60)...,
                                displayedComponents: .hourAndMinute
                            ){
                                Text("Время доставки")
                                    .bodyLarge()
                            }
                            .padding(.top, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        }else{
                            DatePicker(
                                selection: $deferredTime.onChange(
                                    { date in
                                        viewModel.kmmViewModel.onDeferredTimeSelected(
                                            deferredTime: Time(
                                                hours: Int32(calendar.component(.hour, from: date)),
                                                minutes: Int32(calendar.component(.minute, from: date)
                                                              )
                                            )
                                        )
                                    }
                                ),
                                in: (Date.now + 60 * 60)...,
                                displayedComponents: .hourAndMinute
                            ){
                                Text("Время самовывоза")
                                    .bodyLarge()
                            }
                            .padding(.top, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                    }
                }
            }
            .background(AppColor.background)
            
            VStack(spacing:0){
                if let discount = viewModel.creationOrderViewState.discount{
                    HStack(spacing:0){
                        Text("create_order_discount")
                            .bodyMedium()
                            .foregroundColor(AppColor.onSurface)
                        
                        Spacer()
                        
                        DiscountCard(text:discount)
                    }.padding(.top, 8)
                        .padding(.horizontal, 16)
                }
                
                HStack(spacing:0){
                    Text(Strings.MSG_CREATION_ORDER_RESULT)
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                    Spacer()
                    if let totalCost = viewModel.creationOrderViewState.totalCost{
                        let totaCostString = "\(totalCost)\(Strings.CURRENCY)"
                        Text(totaCostString)
                            .bodyMedium()
                            .foregroundColor(AppColor.onSurface)
                    }
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
         
                if(viewModel.creationOrderViewState.isDelivery){
                    if let deliveryCost = viewModel.creationOrderViewState.deliveryCost {
                        HStack(spacing:0){
                            Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                                .bodyMedium()
                                .foregroundColor(AppColor.onSurface)
                            Spacer()
                            Text("\(deliveryCost)\(Strings.CURRENCY)")
                                .bodyMedium()
                                .foregroundColor(AppColor.onSurface)
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                }
                
                HStack(spacing:0){
                    Text(Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                        .bodyMedium(weight: .bold)
                        .foregroundColor(AppColor.onSurface)
                    Spacer()
                    
                    
                    if let oldFinalCost = viewModel.creationOrderViewState.oldFinalCost{
                        Text("\(oldFinalCost)" + Strings.CURRENCY)
                            .strikethrough()
                            .bodyMedium(weight: .bold)
                            .foregroundColor(AppColor.onSurfaceVariant)
                            .padding(.trailing, 4)
                    }
                    
                    if let finalCost = viewModel.creationOrderViewState.newFinalCost {
                        Text("\(finalCost)" + Strings.CURRENCY)
                            .bodyMedium(weight: .bold)
                            .foregroundColor(AppColor.onSurface)
                    }
                    
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                
                Button(
                    action: {
                        viewModel.createOrder()
                    }, label: {
                        ButtonText(text: Strings.ACTION_CART_PRODUCT_CREATE_ORDER)
                    }
                )
                .padding(.vertical, Diems.MEDIUM_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(AppColor.surface)
        }
        .background(AppColor.surface)
        .onReceive(viewModel.$creationOrderViewState, perform: { creationOrderViewState in
            if(creationOrderViewState.isDelivery){
                addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
            }else{
                addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_CAFE
            }
            
            print(creationOrderViewState.eventList)
            
            creationOrderViewState.eventList.forEach { event in
                switch(event){
                case is CreateOrderEventShowUserAddressError : showAddressError = true
                case is CreateOrderEventShowSomethingWentWrongErrorEvent : showCommonError = true
                case is CreateOrderEventOrderCreatedEvent : isRootActive = false
                    selection = 2
                    showOrderCreated = true
                case is CreateOrderEventShowCafeAddressListEvent :
                    addressList = (event as? CreateOrderEventShowCafeAddressListEvent)?.addressList ?? []
                    goToCafeAddress = true
                case is CreateOrderEventShowUserAddressListEvent : goToUserAddress = true
                case is CreateOrderEventShowPaymentMethodList :
                    paymentList = (event as? CreateOrderEventShowPaymentMethodList)?.selectablePaymentMethodList ?? []
                    goToSelectPaymentMethod = true
                case is CreateOrderEventShowPaymentMethodError:
                    showPaymentMethodError = true
                default:
                    print("def")
                }
            }
            
            if !creationOrderViewState.eventList.isEmpty{
                viewModel.kmmViewModel.consumeEventList(eventList: creationOrderViewState.eventList)
            }
        })
    }
}

