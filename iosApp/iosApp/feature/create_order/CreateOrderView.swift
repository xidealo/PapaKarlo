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
    
    // errors ----------------
    @State var showCreatedAddress: Bool = false
    @State var showUserAddressError: Bool = false
    @State var showOrderNotAvailableError: Bool = false
    @State var showCommonError: Bool = false
    @State var showPaymentMethodError: Bool = false
    // ----------------
    
    @State var goToUserAddress: Bool = false
    @State var goToCafeAddress: Bool = false
    @State var goToSelectPaymentMethod: Bool = false
    
    //for back after createOrder
    @Binding var isRootActive: Bool
    @Binding var selection: MainContainerState
    @Binding var showOrderCreated: Bool
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var addressList: [SelectableCafeAddressItem] = []
    @State var paymentList: [SelectablePaymentMethod] = []
    @State var changeError : LocalizedStringKey?
    
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    
    @State var viewModel = CreateOrderViewModel(
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        cafeInteractor: iosComponent.provideCafeInteractor(),
        userInteractor: iosComponent.provideIUserInteractor(),
        getSelectableUserAddressList: iosComponent.provideGetSelectableUserAddressListUseCase(),
        getSelectableCafeList: iosComponent.provideGetSelectableCafeListUseCase(),
        getCartTotalFlowUseCase: iosComponent.provideGetCartTotalUseCase(),
        getMotivationUseCase: iosComponent.provideGetMotivationUseCaseUseCase(),
        getMinTime: iosComponent.provideGetMinTimeUseCase(),
        createOrder: iosComponent.provideCreateOrderUseCase(),
        getSelectedCityTimeZone: iosComponent.provideGetSelectedCityTimeZoneUseCase(),
        saveSelectedUserAddress : iosComponent.provideSaveSelectedUserAddressUseCase(),
        getSelectablePaymentMethodListUseCase : iosComponent.provideGetSelectablePaymentMethodListUseCase(),
        savePaymentMethodUseCase : iosComponent.provideSavePaymentMethodUseCase(),
        isOrderAvailableUseCase: iosComponent.provideIsOrderAvailableUseCase()
    )
    
    @State var createOrderViewState: CreateOrderViewState? = nil
    
    var body: some View {
        VStack(spacing: 0){
            ToolbarView(
                title: "titleCreationOrder",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            NavigationLink(
                destination: UserAddressListView(
                    title: "title_delivery_addresses",
                    isClickable: true,
                    closedCallback: {
                        viewModel.onAction(
                            action: CreateOrderActionHideDeliveryAddressList()
                        )
                    }
                ),
                isActive: $goToUserAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination: CafeAddressListView(
                    isClickable: true,
                    _title: "title_pickup_addresses",
                    addressList: createOrderViewState?.pickupAddressList.addressList ?? [],
                    _closedCallback: {
                        viewModel.onAction(
                            action: CreateOrderActionHidePickupAddressList()
                        )
                    }
                ),
                isActive: $goToCafeAddress
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination: SelectablePaymentListView(
                    paymentList: createOrderViewState?.paymentMethodList.paymentMethodList ?? [],
                    closedCallback: { paymentUuid in
                        if let paymentUuid = paymentUuid {
                            viewModel.onAction(
                                action: CreateOrderActionChangePaymentMethod(paymentMethodUuid: paymentUuid)
                            )
                            return
                        }
                        
                        viewModel.onAction(
                            action: CreateOrderActionHidePaymentMethodList()
                        )
                    }
                ),
                isActive: $goToSelectPaymentMethod
            ){
                EmptyView()
            }
            
            if(createOrderViewState?.isLoading == true || createOrderViewState?.isLoading == nil){
                LoadingView()
            }else{
                if let createOrderViewStateNN = createOrderViewState {
                    CreateOrderSuccessView(
                        showCreatedAddress: $showCreatedAddress,
                        goToUserAddress: $goToUserAddress,
                        goToCafeAddress: $goToCafeAddress,
                        goToSelectPaymentMethod: $goToSelectPaymentMethod,
                        changeError: $changeError,
                        isRootActive: $isRootActive,
                        selection: $selection,
                        showOrderCreated: $showOrderCreated,
                        createOrderViewState: createOrderViewStateNN,
                        action: viewModel.onAction
                    )
                }
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
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
                toast: Toast(title: "Что-то пошло не так"),
                show: $showCommonError,
                backgroundColor: AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showCommonError
        ) .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Способ оплаты не выбран"),
                show: $showPaymentMethodError,
                backgroundColor: AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showPaymentMethodError
        ).overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес не выбран"),
                show: $showUserAddressError,
                backgroundColor: AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showUserAddressError
        ).overlay(
            overlayView: ToastView(
                toast: Toast(title: "Заказы временно не принимаются"),
                show: $showOrderNotAvailableError,
                backgroundColor: AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showOrderNotAvailableError
        )
    }
    
    func subscribe(){
        viewModel.onAction(action: CreateOrderActionUpdate())
        listener = viewModel.dataState.watch { createOrderDataState in
            if let createOrderDataStateNN = createOrderDataState {
                
                goToUserAddress = createOrderDataStateNN.isUserAddressListShown
                goToCafeAddress = createOrderDataStateNN.isCafeListShown
                goToSelectPaymentMethod = createOrderDataStateNN.isPaymentMethodListShown
                
                if(createOrderDataStateNN.isChangeErrorShown){
                    changeError = "error_enter_correct_amount"
                }else{
                    changeError = nil
                }
                
                createOrderViewState = CreateOrderViewState(
                    isDelivery: createOrderDataStateNN.isDelivery,
                    deliveryAddress: createOrderDataStateNN.selectedUserAddress?.getAddress(),
                    pickupAddress: createOrderDataStateNN.selectedCafe?.address,
                    isAddressErrorShown: createOrderDataStateNN.isDelivery && createOrderDataStateNN.isAddressErrorShown,
                    deferredTime: getDeferredTimeString(deferredTime: createOrderDataStateNN.deferredTime),
                    deferredTimeStringLocolized: getDeferredTimeStringId(isDelivery: createOrderDataStateNN.isDelivery),
                    selectedPaymentMethod: getPaymentMethodUI(paymentMethod: createOrderDataStateNN.selectedPaymentMethod),
                    isPaymentMethodErrorShown: createOrderDataStateNN.isPaymentMethodErrorShown,
                    comment: createOrderDataStateNN.comment,
                    cartTotal: getCartTotalUI(cartTotal: createOrderDataStateNN.cartTotal),
                    isLoading: createOrderDataStateNN.isLoading,
                    deliveryAddressList: DeliveryAddressListUI(
                        isShown: createOrderDataStateNN.isUserAddressListShown,
                        addressList: createOrderDataStateNN.userAddressList.map({ selectableUserAddress in
                            SelectableAddressUI(
                                uuid: selectableUserAddress.address.uuid,
                                address: selectableUserAddress.address.getAddress(),
                                isSelected: selectableUserAddress.isSelected
                            )
                        })
                    ),
                    pickupAddressList: PickupAddressListUI(
                        isShown: createOrderDataStateNN.isCafeListShown,
                        addressList: createOrderDataStateNN.cafeList.map({ selectableCafe in
                            SelectableAddressUI(
                                uuid: selectableCafe.cafe.uuid,
                                address: selectableCafe.cafe.address,
                                isSelected: selectableCafe.isSelected
                            )
                        })
                    ),
                    isDeferredTimeShown: createOrderDataStateNN.isDeferredTimeShown,
                    timePicker: TimePickerUI(
                        isShown: createOrderDataStateNN.isTimePickerShown,
                        minTime: TimeUI(
                            hours: Int(createOrderDataStateNN.minDeferredTime.hours),
                            minutes: Int(createOrderDataStateNN.minDeferredTime.minutes)
                        ),
                        initialTime: TimeUI(
                            hours: Int(createOrderDataStateNN.initialDeferredTime.hours),
                            minutes: Int(createOrderDataStateNN.initialDeferredTime.minutes)
                        )
                    ),
                    paymentMethodList: PaymentMethodListUI(
                        isShown: createOrderDataStateNN.isPaymentMethodListShown,
                        paymentMethodList: createOrderDataStateNN.paymentMethodList.map({ selectablePaymentMethod in
                            SelectablePaymentMethodUI(
                                id: selectablePaymentMethod.paymentMethod.uuid,
                                name: selectablePaymentMethod.paymentMethod.name.getPaymentMethod(),
                                isSelected: selectablePaymentMethod.isSelected
                            )
                        })
                    ),
                    showChange: createOrderDataStateNN.paymentByCash,
                    withoutChange: "msg_without_change",
                    changeFrom: "msg_change_from",
                    withoutChangeChecked:   createOrderDataStateNN.withoutChangeChecked,
                    change: getChange(change: createOrderDataStateNN.change),
                    isChangeErrorShown: createOrderDataStateNN.isChangeErrorShown,
                    isOrderCreationEnabled: createOrderDataStateNN.isOrderCreationEnabled
                )
            }
        }
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events {
                let createOrderEvents = events as? [CreateOrderEvent] ?? []
                
                createOrderEvents.forEach { event in
                    print(event)
                    
                    switch(event){
                    case is CreateOrderEventOpenCreateAddressEvent :
                        print("CreateOrderEventOpenCreateAddressEvent but open from navview")
                    case is CreateOrderEventShowUserUnauthorizedErrorEvent :
                        showCommonError = true
                    case is CreateOrderEventShowSomethingWentWrongErrorEvent:
                        showCommonError = true
                    case is CreateOrderEventOrderCreatedEvent:
                        isRootActive = false
                        selection = MainContainerState.profile
                        showOrderCreated = true
                    case is CreateOrderEventShowUserAddressError:
                        showUserAddressError = true
                    case is CreateOrderEventShowPaymentMethodError:
                        showPaymentMethodError = true   
                    case is CreateOrderEventOrderNotAvailableErrorEvent:
                        showOrderNotAvailableError = true
                    default:
                        print("def")
                    }
                }
                if !createOrderEvents.isEmpty {
                    viewModel.consumeEvents(events: createOrderEvents)
                }
            }
        }
        )
    }
    
    func unsubscribe() {
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
    
    
    func getDeferredTimeStringId(isDelivery:Bool) -> LocalizedStringKey{
        if(isDelivery) {
            return "title_create_order_time_delivery"
        } else {
            return "title_create_order_time_pickup"
        }
    }
    
    func getDeferredTimeString(deferredTime: CreateOrderDeferredTime) -> String {
        switch deferredTime {
        case _ as CreateOrderDeferredTimeAsap:
            return NSLocalizedString("asap", comment: "ASAP delivery time")
        case let defTime as CreateOrderDeferredTimeLater:
            return "\(defTime.time.hours.withFirstZero()):\(defTime.time.minutes.withFirstZero())"
        default:
            return  ""
        }
    }
    
    func getPaymentValue(valueToShow: String?, valueToCopy: String?) -> PaymentMethodValueUI? {
        if let valueToShowNN = valueToShow {
            if let valueToCopyNN = valueToCopy {
                return PaymentMethodValueUI(
                    value: valueToShowNN,
                    valueToCopy: valueToCopyNN
                )
            }
        }
        
        return nil
    }
    
    
    func getCartTotalUI(cartTotal:CreateOrderCartTotal) -> CartTotalUI {
        switch cartTotal {
        case _ as CreateOrderCartTotalLoading :
            return CartTotalUI.Loading
        case let cartTotalSuccess as CreateOrderCartTotalSuccess:
            return CartTotalUI.Success(
                cartTotalSuccess.motivation?.getMotivationUi(),
                cartTotalSuccess.discount,
                cartTotalSuccess.deliveryCost,
                cartTotalSuccess.oldFinalCost,
                cartTotalSuccess.newFinalCost
            )
        default:
            return CartTotalUI.Loading
        }
    }
    
    func getPaymentMethodUI(paymentMethod:PaymentMethod?) -> PaymentMethodUI? {
        if let paymentMethodNN = paymentMethod{
            return  PaymentMethodUI(
                uuid: paymentMethodNN.uuid,
                name: paymentMethodNN.name.getPaymentMethod(),
                value: getPaymentValue(
                    valueToShow: paymentMethodNN.valueToShow,
                    valueToCopy: paymentMethodNN.valueToCopy
                )
            )
            
        }
        return nil
    }
    
    func getChange(change:KotlinInt?) -> String{
        if let change = change {
            return "\(change)"
        }
        
        return ""
    }
}

struct CreateOrderSuccessView: View {
    
    @State var addressLable = Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY
    // errors
    @Binding var showCreatedAddress: Bool

    //navigations
    @Binding var goToUserAddress: Bool
    @Binding var goToCafeAddress: Bool
    @Binding var goToSelectPaymentMethod: Bool
    
    @State var isDelivery = true
    @State var comment = ""
    @State var changeTextField = ""
    @State var faster = true
    @State var deferredTime: Foundation.Date = Foundation.Date()
    @Binding var changeError : LocalizedStringKey?
    
    @Binding var isRootActive: Bool
    @Binding var selection: MainContainerState
    @Binding var showOrderCreated: Bool
    let calendar = Calendar.current
    
    let createOrderViewState: CreateOrderViewState
    let action: (CreateOrderAction) -> Void
    
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
                            action(CreateOrderActionChangeMethod(position: 0))
                        }else{
                            action(CreateOrderActionChangeMethod(position: 1))
                        }
                    }
                    .padding(.top, Diems.MEDIUM_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    
                    if(createOrderViewState.isDelivery){
                        if createOrderViewState.deliveryAddress == nil {
                            NavigationCardView(
                                icon: nil,
                                label: addressLable,
                                destination: CreateAddressView(show: $showCreatedAddress)
                            )
                            .padding(.top, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        } else {
                            ActionTextCardView(
                                placeHolder: LocalizedStringKey("title_delivery_address").stringValue(),
                                text: createOrderViewState.deliveryAddress ?? ""
                            ){
                                action(CreateOrderActionDeliveryAddressClick())
                            }
                            .padding(.top, Diems.SMALL_PADDING)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                    } else {
                        ActionTextCardView(
                            placeHolder: LocalizedStringKey("title_pickup_address").stringValue(),
                            text: "\(createOrderViewState.pickupAddress ?? "")"
                        ){
                            action(CreateOrderActionPickupAddressClick())
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                    
                    if(createOrderViewState.isAddressErrorShown){
                        Text("error_select_delivery_address")
                            .bodySmall()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .foregroundColor(AppColor.error)
                            .padding(.top, 4)
                            .padding(.horizontal, 16)
                            .padding(.leading, 16)
                    }
                    
                    if(createOrderViewState.selectedPaymentMethod == nil){
                        ActionCardView(
                            icon: nil,
                            label: "Способ оплаты",
                            isSystemImageName: false,
                            isShowRightArrow: true
                        ){
                            action(CreateOrderActionPaymentMethodClick())
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }else{
                        ActionLocalizedTextCardView(
                            placeHolder: "selectable_payment_method",
                            text: createOrderViewState.selectedPaymentMethod?.name ?? ""
                        ){
                            action(CreateOrderActionPaymentMethodClick())
                        }
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.horizontal, Diems.MEDIUM_PADDING)
                    }
                    
                    if(createOrderViewState.isPaymentMethodErrorShown){
                        Text("error_select_payment_method")
                            .bodySmall()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .foregroundColor(AppColor.error)
                            .padding(.top, 4)
                            .padding(.horizontal, 16)
                            .padding(.leading, 16)
                    }
                    
                    if(createOrderViewState.showChange){
                        HStack(spacing:0){
                            Button(action: {
                                action(
                                    CreateOrderActionChangeWithoutChangeChecked(
                                        isChecked: !createOrderViewState.withoutChangeChecked
                                    )
                                )
                            }) {
                                FoodDeliveryCheckBox(
                                    isSelected: createOrderViewState.withoutChangeChecked,
                                    action: {
                                        action(
                                            CreateOrderActionChangeWithoutChangeChecked(
                                                isChecked: !createOrderViewState.withoutChangeChecked
                                            )
                                        )
                                    }
                                )
                                
                                Text("msg_without_change")
                                    .foregroundColor(AppColor.onSurface)
                                    .bodyMedium()
                                
                                Spacer()
                            }
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 8)
                            .padding(.horizontal, 16)
                        }
                        .padding(.top, 8)
                        
                        if(!createOrderViewState.withoutChangeChecked){
                            EditTextView(
                                hint: "С какой суммы подготовить сдачу?*",
                                text: $changeTextField,
                                limit: 10,
                                keyBoadrType: UIKeyboardType.numberPad,
                                errorMessage: $changeError,
                                textChanged: { comment in
                                    action(CreateOrderActionChangeChange(change: changeTextField))
                                }
                            )
                            .padding(.top, 8)
                            .padding(.horizontal, 16)
                        }
                    }
                    
                    EditTextView(
                        hint: Strings.HINT_CREATE_COMMENT_COMMENT,
                        text: $comment,
                        limit: 255,
                        errorMessage: .constant(nil),
                        textChanged: { comment in
                            action(CreateOrderActionChangeComment(comment: comment))
                        }
                    )
                    .padding(.top, 8)
                    .padding(.horizontal, 16)
                    
                    Toggle(
                        isOn: $faster.onChange(
                            { faster in
                                if(faster) {
                                    action(CreateOrderActionAsapClick())
                                }else{
                                    let date =  Date.now + 60 * 60
                                    action(
                                        CreateOrderActionChangeDeferredTime(
                                            time: Time(
                                                hours: Int32(calendar.component(.hour, from: date)),
                                                minutes: Int32(
                                                    calendar.component(.minute, from: date)
                                                    )
                                            )
                                        )
                                    )
                                    
                                }
                            }
                        )
                    ){
                        Text("Как можно скорее")
                            .bodyLarge()
                    }
                    .toggleStyle(.automatic)
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    
                    if(!faster){
                        if(createOrderViewState.isDelivery){
                            DatePicker(
                                selection: $deferredTime.onChange(
                                    { date in
                                        action(
                                            CreateOrderActionChangeDeferredTime(
                                                time: Time(
                                                    hours: Int32(calendar.component(.hour, from: date)),
                                                    minutes: Int32(calendar.component(.minute, from: date)
                                                                  )
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
                                        action(
                                            CreateOrderActionChangeDeferredTime(
                                                time: Time(
                                                    hours: Int32(calendar.component(.hour, from: date)),
                                                    minutes: Int32(calendar.component(.minute, from: date)
                                                                  )
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
                
                switch createOrderViewState.cartTotal {
                case .Loading:
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppColor.primary))
                        .scaleEffect(1.0)
                        .padding(8)
                case .Success(
                    let motivationUi,
                    let discount,
                    let deliveryCost,
                    let oldFinalCost,
                    let newFinalCost
                ):
                    BottomAmountBarSuccessView(
                        motivation: motivationUi,
                        discount: discount,
                        deliveryCost: deliveryCost,
                        oldFinalCost: oldFinalCost,
                        newFinalCost: newFinalCost
                    )
                }
                
                Button(
                    action: {
                        action(
                            CreateOrderActionCreateClick(
                                withoutChange: createOrderViewState.withoutChange.stringValue(),
                                changeFrom: createOrderViewState.changeFrom.stringValue()
                            )
                        )
                    }, label: {
                        ButtonText(
                            text: Strings.ACTION_CART_PRODUCT_CREATE_ORDER,
                            background: createOrderViewState.isOrderCreationEnabled ?  AppColor.primary : AppColor.disabled,
                            foregroundColor: createOrderViewState.isOrderCreationEnabled ? AppColor.onPrimary : AppColor.onDisabled
                        )
                    }
                )
                .disabled(!createOrderViewState.isOrderCreationEnabled)
                .padding(.vertical, Diems.MEDIUM_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(AppColor.surface)
        }
        .background(AppColor.surface)
    }
}

struct BottomAmountBarSuccessView: View {
    let motivation : MotivationUi?
    let discount: String?
    let deliveryCost: String?
    let oldFinalCost: String?
    let newFinalCost: String
    
    var body: some View{
        VStack(spacing: 0){
            if let motivation = motivation {
                Motivation(motivation: motivation)
            }
            
            if let discount = discount {
                HStack(spacing:0) {
                    Text("create_order_discount")
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                    
                    Spacer()
                    
                    DiscountCard(text:discount)
                }.padding(.top, 8)
                    .padding(.horizontal, 16)
            }
            
            if let deliveryCost = deliveryCost {
                HStack(spacing:0){
                    Text(Strings.MSG_CREATION_ORDER_DELIVERY)
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                    Spacer()
                    Text(deliveryCost)
                        .bodyMedium()
                        .foregroundColor(AppColor.onSurface)
                }
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            
            HStack(spacing:0){
                Text(Strings.MSG_CREATION_ORDER_FINAL_AMOUNT)
                    .bodyMedium(weight: .bold)
                    .foregroundColor(AppColor.onSurface)
                Spacer()
                
                if let oldFinalCost = oldFinalCost {
                    Text(oldFinalCost)
                        .strikethrough()
                        .bodyMedium(weight: .bold)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .padding(.trailing, 4)
                }
                
                Text(newFinalCost)
                    .bodyMedium(weight: .bold)
                    .foregroundColor(AppColor.onSurface)
                
            }
            .padding(.top, Diems.SMALL_PADDING)
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            
        }
    }
}
