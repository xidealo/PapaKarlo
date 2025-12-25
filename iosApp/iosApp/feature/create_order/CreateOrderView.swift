//
//  CreateOrderView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import Combine
import shared
import SwiftUI

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
    @State var goToCreateAddress: Bool = false

    // for back after createOrder
    @Binding var isRootActive: Bool
    @Binding var openProfileScreen: Bool
    @Binding var showOrderCreated: Bool

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var addressList: [SelectableCafeAddressItem] = []
    @State var paymentList: [SelectablePaymentMethod] = []
    @State var changeError: LocalizedStringKey?
    @State var additionalUtensilsError: LocalizedStringKey?

    @State var listener: Closeable?
    @State var eventsListener: Closeable?

    @State var viewModel = CreateOrderViewModel(
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        cafeInteractor: iosComponent.provideCafeInteractor(),
        userInteractor: iosComponent.provideIUserInteractor(),
        getSelectableUserAddressList: iosComponent.provideGetSelectableUserAddressListUseCase(),
        getCurrentUserAddressWithCityUseCase: iosComponent.provideGetCurrentUserAddressWithCityUseCase(),
        getSelectableCafeList: iosComponent.provideGetSelectableCafeListUseCase(),
        getCartTotalFlowUseCase: iosComponent.provideGetCartTotalUseCase(),
        getMotivationUseCase: iosComponent.provideGetMotivationUseCaseUseCase(),
        getMinTime: iosComponent.provideGetMinTimeUseCase(),
        createOrder: iosComponent.provideCreateOrderUseCase(),
        getSelectedCityTimeZone: iosComponent.provideGetSelectedCityTimeZoneUseCase(),
        saveSelectedUserAddress: iosComponent.provideSaveSelectedUserAddressUseCase(),
        getSelectablePaymentMethodListUseCase: iosComponent.provideGetSelectablePaymentMethodListUseCase(),
        savePaymentMethodUseCase: iosComponent.provideSavePaymentMethodUseCase(),
        isDeliveryEnabledFromCafeUseCase: iosComponent.provideIsDeliveryEnabledFromCafeUseCase(),
        isPickupEnabledFromCafeUseCase: iosComponent.provideIsPickupEnabledFromCafeUseCase(),
        hasOpenedCafeUseCase: iosComponent.provideHasOpenedCafeUseCase(),
        getWorkloadCafeUseCase: iosComponent.provideGetWorkloadCafeUseCase(),
        getSelectedPaymentMethodUseCase: iosComponent.provideGetSelectedPaymentMethodUseCase(),
        getExtendedCommentUseCase: iosComponent.provideGetExtendedCommentUseCase(),
        getAdditionalUtensilsUseCase: iosComponent.provideGetAdditionalUtensilsUseCase(),
        getDeferredTimeHintUseCase: iosComponent.provideGetDeferredTimeHintUseCase()
    )

    @State var createOrderViewState: CreateOrderViewState?

    var body: some View {
        VStack(spacing: 0) {
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
            ) {
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
            ) {
                EmptyView()
            }
            NavigationLink(
                destination: CreateAddressView(show: $showCreatedAddress),
                isActive: $goToCreateAddress
            ) {
                EmptyView()
            }

            if createOrderViewState?.isLoadingSwitcher == true {
                LoadingView()
            } else {
                if let createOrderViewStateNN = createOrderViewState {
                    CreateOrderSuccessView(
                        showCreatedAddress: $showCreatedAddress,
                        goToUserAddress: $goToUserAddress,
                        goToCafeAddress: $goToCafeAddress,
                        goToSelectPaymentMethod: $goToSelectPaymentMethod,
                        goToCreateAddress: $goToCreateAddress,
                        changeError: $changeError,
                        additionalUtensilsError: $additionalUtensilsError,
                        isRootActive: $isRootActive,
                        showOrderCreated: $showOrderCreated,
                        createOrderViewState: createOrderViewStateNN,
                        action: viewModel.onAction
                    )
                }
            }
        }
        .hiddenNavigationBarStyle()
        .onAppear {
            subscribe()
            eventsSubscribe()
        }
        .onDisappear {
            unsubscribe()
        }.overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес добавлен"),
                show: $showCreatedAddress,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $showCreatedAddress
        ).overlay(
            overlayView: ToastView(
                toast: Toast(title: "Что-то пошло не так"),
                show: $showCommonError,
                backgroundColor: AppColor.error,
                foregroundColor: AppColor.onError
            ),
            show: $showCommonError
        ).overlay(
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

    func subscribe() {
        viewModel.onAction(action: CreateOrderActionInit())
        listener = viewModel.dataState.watch { createOrderDataState in
            if let createOrderDataStateNN = createOrderDataState {
                goToUserAddress = createOrderDataStateNN.isUserAddressListShown
                goToCafeAddress = createOrderDataStateNN.isCafeListShown
                goToSelectPaymentMethod = createOrderDataStateNN.isPaymentMethodListShown

                if createOrderDataStateNN.isChangeErrorShown {
                    changeError = "error_enter_correct_amount"
                } else {
                    changeError = nil
                }

                if createOrderDataStateNN.isAdditionalUtensilsErrorShown {
                    additionalUtensilsError = "error_additional_utensils"
                } else {
                    additionalUtensilsError = nil
                }

                print(createOrderDataStateNN)
                createOrderViewState = CreateOrderViewState(
                    createOrderType: getCreateOrderType(createOrderDataState: createOrderDataStateNN),
                    isAddressErrorShown: createOrderDataStateNN.isDelivery && (createOrderDataStateNN.isAddressErrorShown == .error),
                    deferredTime: getDeferredTimeString(deferredTime: createOrderDataStateNN.deferredTime),
                    deferredTimeStringId: getDeferredTimeStringId(isDelivery: createOrderDataStateNN.isDelivery),
                    selectedPaymentMethod: getPaymentMethodUI(paymentMethod: createOrderDataStateNN.selectedPaymentMethod),
                    isPaymentMethodErrorShown: createOrderDataStateNN.isPaymentMethodErrorShown,
                    showChange: createOrderDataStateNN.paymentByCash,
                    withoutChange: "msg_without_change",
                    changeFrom: "msg_change_from",
                    withoutChangeChecked: createOrderDataStateNN.withoutChangeChecked,
                    change: getChange(change: createOrderDataStateNN.change),
                    isChangeErrorShown: createOrderDataStateNN.isChangeErrorShown,
                    comment: createOrderDataStateNN.comment,
                    cartTotal: getCartTotalUI(cartTotal: createOrderDataStateNN.cartTotal),
                    isLoadingCreateOrder: createOrderDataStateNN.isLoading,
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
                        paymentMethodList: createOrderDataStateNN.paymentMethodList.map { selectablePaymentMethod in
                            SelectablePaymentMethodUI(
                                id: selectablePaymentMethod.paymentMethod.uuid,
                                name: selectablePaymentMethod.paymentMethod.name.getPaymentMethod(),
                                isSelected: selectablePaymentMethod.isSelected
                            )
                        }
                    ),
                    isOrderCreationEnabled: createOrderDataStateNN.isDelivery ?
                    (createOrderDataStateNN.deliveryState == .enabled) : createOrderDataStateNN.isPickupEnabled,
                    isLoadingSwitcher: createOrderDataStateNN.isLoadingSwitcher,
                    additionalUtensils: createOrderDataStateNN.additionalUtensils,
                    additionalUtensilsName: "msg_additional_utensils",
                    additionalUtensilsCount: createOrderDataStateNN.additionalUtensilsCount,
                    isAdditionalUtensilsErrorShown: createOrderDataStateNN.isAdditionalUtensilsErrorShown,

                )
            }
        }
    }

    func getCreateOrderType(createOrderDataState: CreateOrderDataState) -> CreateOrderType {
        if createOrderDataState.isDelivery {
            getCreateOrderTypeDelivery(dataState: createOrderDataState)
        } else {
            getCreateOrderTypePickup(dataState: createOrderDataState)
        }
    }

    private func getCreateOrderTypeDelivery(dataState: CreateOrderDataState) -> CreateOrderType {
        let delivery = CreateOrderType.Delivery(
            deliveryAddress: getDelivryAddress(dataState: dataState),
            deliveryAddressList: DeliveryAddressListUI(
                isShown: dataState.isUserAddressListShown,
                addressList: dataState.userAddressList.map { selectableUserAddress in
                    SelectableAddressUI(
                        uuid: selectableUserAddress.address.uuid,
                        address: selectableUserAddress.address.getAddress(),
                        isSelected: selectableUserAddress.isSelected,
                        isEnabled: true
                    )
                }
            ),
            state: {
                switch dataState.deliveryState {
                case .notEnabled:
                    return .notEnabled
                case .enabled:
                    return .enabled
                case .needAddress:
                    return .needAddress
                default:
                    return .notEnabled
                }
            }(),
            workload: {
                switch dataState.workload {
                case .low:
                    return .low
                case .average:
                    return .average
                case .high:
                    return .high
                default:
                    return .low
                }
            }()
        )
        return .delivery(delivery)
    }

    private func getDelivryAddress(dataState: CreateOrderDataState) -> String? {

        if dataState.selectedUserAddressWithCity == nil {
            return nil
        }

        return (dataState.selectedUserAddressWithCity?.city ?? "") + Constants().ADDRESS_DIVIDER + (dataState.selectedUserAddressWithCity?.userAddress?.getAddress() ?? "")
    }

    private func getCreateOrderTypePickup(dataState: CreateOrderDataState) -> CreateOrderType {
        let pickup = CreateOrderType.Pickup(
            pickupAddress: dataState.selectedCafe?.address,
            pickupAddressList: PickupAddressListUI(
                isShown: dataState.isCafeListShown,
                addressList: dataState.cafeList.map { selectableCafe in
                    SelectableAddressUI(
                        uuid: selectableCafe.cafe.uuid,
                        address: selectableCafe.cafe.address,
                        isSelected: selectableCafe.isSelected,
                        isEnabled: selectableCafe.canBePickup
                    )
                }
            ),
            hasOpenedCafe: dataState.hasOpenedCafe,
            isEnabled: dataState.isPickupEnabled
        )
        return .pickup(pickup)
    }

    func eventsSubscribe() {
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events {
                let createOrderEvents = events as? [CreateOrderEvent] ?? []

                for event in createOrderEvents {
                    print(event)

                    switch event {
                    case is CreateOrderEventOpenCreateAddressEvent:
                        print("CreateOrderEventOpenCreateAddressEvent but open from navview")
                    case is CreateOrderEventShowUserUnauthorizedErrorEvent:
                        showCommonError = true
                    case is CreateOrderEventShowSomethingWentWrongErrorEvent:
                        showCommonError = true
                    case is CreateOrderEventOrderCreatedEvent:
                        isRootActive = false
                        showOrderCreated = true
                        openProfileScreen = true
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

    func getDeferredTimeStringId(isDelivery: Bool) -> LocalizedStringKey {
        if isDelivery {
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
            return ""
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

    func getCartTotalUI(cartTotal: CreateOrderCartTotal) -> CartTotalUI {
        switch cartTotal {
        case _ as CreateOrderCartTotalLoading:
            return CartTotalUI.loading
        case let cartTotalSuccess as CreateOrderCartTotalSuccess:
            return CartTotalUI.success(
                CartTotalUI.Success(
                    motivation: cartTotalSuccess.motivation?.getMotivationUi(),
                    discount: cartTotalSuccess.discount,
                    deliveryCost: cartTotalSuccess.deliveryCost,
                    oldFinalCost: cartTotalSuccess.oldFinalCost,
                    newFinalCost: cartTotalSuccess.newFinalCost
                )
            )
        default:
            return CartTotalUI.loading
        }
    }

    func getPaymentMethodUI(paymentMethod: PaymentMethod?) -> PaymentMethodUI? {
        if let paymentMethodNN = paymentMethod {
            return PaymentMethodUI(
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

    func getChange(change: KotlinInt?) -> String {
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

    // navigations
    @Binding var goToUserAddress: Bool
    @Binding var goToCafeAddress: Bool
    @Binding var goToSelectPaymentMethod: Bool
    @Binding var goToCreateAddress: Bool

    @Binding var changeError: LocalizedStringKey?
    @Binding var additionalUtensilsError: LocalizedStringKey?

    @Binding var isRootActive: Bool
    @Binding var showOrderCreated: Bool

    let createOrderViewState: CreateOrderViewState
    let action: (CreateOrderAction) -> Void

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                VStack(spacing: 0) {
                    Switcher(
                        leftTitle: Strings.MSG_CREATION_ORDER_DELIVERY,
                        rightTitle: Strings.MSG_CREATION_ORDER_PICKUP,
                        isLeftSelected: {
                            if case .delivery = createOrderViewState.createOrderType {
                                return true
                            } else {
                                return false
                            }
                        }()
                    ) { isDelivery in
                        if isDelivery {
                            action(CreateOrderActionChangeMethod(position: 0))
                        } else {
                            action(CreateOrderActionChangeMethod(position: 1))
                        }
                    }
                    .padding(.top, Diems.MEDIUM_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)

                    switch createOrderViewState.createOrderType {
                    case let .pickup(pickup):
                        PickupContentView(
                            pickup: pickup,
                            state: createOrderViewState,
                            action: action,
                            changeError: $changeError,
                            additionalUtensilsError: $additionalUtensilsError,
                            goToCafeAddress: $goToCafeAddress
                        )
                    case let .delivery(delivery):
                        DeliveryContentView(
                            delivery: delivery,
                            state: createOrderViewState,
                            action: action,
                            changeError: $changeError,
                            additionalUtensilsError: $additionalUtensilsError,
                            goToCreateAddress: $goToCreateAddress
                        )
                    }
                }
                .background(AppColor.surface)
            }
            VStack(spacing: 0) {
                switch createOrderViewState.cartTotal {
                case .loading:
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppColor.primary))
                        .scaleEffect(1.0)
                        .padding(8)
                case let .success(success):
                    BottomAmountBarSuccessView(
                        motivation: success.motivation,
                        discount: success.discount,
                        deliveryCost: success.deliveryCost,
                        oldFinalCost: success.oldFinalCost,
                        newFinalCost: success.newFinalCost
                    )
                }

                Button(
                    action: {
                        action(
                            CreateOrderActionCreateClick(
                                withoutChange: createOrderViewState.withoutChange.stringValue(),
                                changeFrom: createOrderViewState.changeFrom.stringValue(),
                                additionalUtensils: createOrderViewState.additionalUtensilsName.stringValue()
                            )
                        )
                    }, label: {
                        if createOrderViewState.isLoadingCreateOrder {
                            ZStack {
                                ProgressView()
                                    .progressViewStyle(
                                        CircularProgressViewStyle(tint: AppColor.primary)
                                    )
                                    .scaleEffect(0.8)
                                    .padding(8)
                            }
                            .frame(maxWidth: .infinity, maxHeight: 40, alignment: .center)
                            .background(AppColor.disabled)
                            .cornerRadius(Diems.BUTTON_RADIUS)
                        } else {
                            ButtonText(
                                text: Strings.ACTION_CART_PRODUCT_CREATE_ORDER,
                                background: createOrderViewState.isOrderCreationEnabled ? AppColor.primary : AppColor.disabled,
                                foregroundColor: createOrderViewState.isOrderCreationEnabled ? AppColor.onPrimary : AppColor.onDisabled
                            )
                        }
                    }
                )
                .disabled(!createOrderViewState.isOrderCreationEnabled && !createOrderViewState.isLoadingCreateOrder)
                .padding(.vertical, Diems.MEDIUM_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
            }
        }.background(AppColor.surface)
    }

    struct DeliveryContentView: View {
        let delivery: CreateOrderType.Delivery
        let state: CreateOrderViewState
        let action: (CreateOrderAction) -> Void
        @Binding var changeError: LocalizedStringKey?
        @Binding var additionalUtensilsError: LocalizedStringKey?

        @Binding var goToCreateAddress: Bool

        var body: some View {
            VStack(spacing: 0) {
                if delivery.deliveryAddress == nil {
                    NavigationCardWithDivider(
                        icon: nil,
                        label: Strings.HINT_CREATION_ORDER_ADDRESS_DELIVERY,
                        value: nil,
                        action: {
                            goToCreateAddress = true
                        }
                    )
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, 16)
                } else {
                    NavigationCardWithDivider(
                        icon: nil,
                        label: LocalizedStringKey("title_delivery_address").stringValue(),
                        value: delivery.deliveryAddress ?? "",
                        action: {
                            action(CreateOrderActionDeliveryAddressClick())
                        }
                    )
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, 16)
                }

                if state.isAddressErrorShown {
                    Text("error_select_delivery_address")
                        .bodySmall()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.error)
                        .padding(.top, 4)
                        .padding(.horizontal, 16)
                        .padding(.leading, 16)
                }

                switch delivery.state {
                case .notEnabled:
                    WarningCard(
                        title: LocalizedStringKey("warning_no_order_available").stringValue(),
                        icon: "ic_warning",
                        cardColor: AppColor.negative
                    ).padding(.top, 16)
                        .padding(.horizontal, 16)

                case .enabled:
                    CommonContentView(
                        state: state,
                        action: action,
                        isDelivery: true,
                        changeError: $changeError,
                        additionalUtensilsError: $additionalUtensilsError
                    )

                    switch delivery.workload {
                    case .low:
                        EmptyView()
                    case .average:
                        WarningCard(
                            title: LocalizedStringKey("msg_create_order_average_traffic").stringValue(),
                            icon: "ic_warning",
                            cardColor: AppColor.warning
                        ).padding(.top, 16)
                            .padding(.horizontal, 16)
                    case .high:
                        WarningCard(
                            title: LocalizedStringKey("msg_create_order_high_traffic").stringValue(),
                            icon: "ic_warning",
                            cardColor: AppColor.negative
                        )
                        .padding(.top, 16)
                        .padding(.horizontal, 16)
                    }

                case .needAddress:
                    WarningCard(
                        title: LocalizedStringKey("error_user_address").stringValue(),
                        icon: "ic_warning",
                        cardColor: AppColor.warning
                    )
                    .padding(.top, 16)
                    .padding(.horizontal, 16)
                }
            }
        }
    }

    struct PickupContentView: View {
        let pickup: CreateOrderType.Pickup
        let state: CreateOrderViewState
        let action: (CreateOrderAction) -> Void
        @Binding var changeError: LocalizedStringKey?
        @Binding var additionalUtensilsError: LocalizedStringKey?
        @Binding var goToCafeAddress: Bool

        var body: some View {
            VStack(spacing: 0) {
                NavigationLink(
                    destination: CafeAddressListView(
                        isClickable: true,
                        _title: "title_pickup_addresses",
                        addressList: pickup.pickupAddressList.addressList,
                        _closedCallback: {
                            action(
                                CreateOrderActionHidePickupAddressList()
                            )
                        }
                    ),
                    isActive: $goToCafeAddress
                ) {
                    EmptyView()
                }

                NavigationCardWithDivider(
                    icon: nil,
                    label: LocalizedStringKey("title_pickup_address").stringValue(),
                    value: "\(pickup.pickupAddress ?? "")",
                    action: {
                        action(CreateOrderActionPickupAddressClick())
                    }
                )
                .padding(.horizontal, 16)

                if pickup.isEnabled {
                    CommonContentView(
                        state: state,
                        action: action,
                        isDelivery: false,
                        changeError: $changeError,
                        additionalUtensilsError: $additionalUtensilsError
                    )
                } else {
                    if pickup.hasOpenedCafe {
                        BannerCard(
                            title: LocalizedStringKey("msg_create_order_chose_cafe").stringValue(),
                            text: LocalizedStringKey("warning_no_order_available").stringValue(),
                            icon: "ic_warning",
                            cardColor: AppColor.warning
                        )
                        .padding(.top, 16)
                        .padding(.horizontal, 16)
                    } else {
                        WarningCard(
                            title: LocalizedStringKey("warning_no_order_available").stringValue(),
                            icon: "ic_warning",
                            cardColor: AppColor.negative
                        )
                        .padding(.top, 16)
                        .padding(.horizontal, 16)
                    }
                }
            }.padding(.top, Diems.SMALL_PADDING)
        }
    }

    struct CommonContentView: View {
        let state: CreateOrderViewState
        let action: (CreateOrderAction) -> Void
        let isDelivery: Bool
        @Binding var changeError: LocalizedStringKey?
        @Binding var additionalUtensilsError: LocalizedStringKey?

        @State var changeTextField = ""
        @State var comment = ""
        @State var additionalUtensilsCountField = ""
        @State var faster = true
        @State var deferredTime: Foundation.Date = .init()
        let calendar = Calendar.current

        var body: some View {
            VStack(spacing: 0) {
                NavigationCardWithDivider(
                    icon: nil,
                    label: LocalizedStringKey("selectable_payment_method").stringValue(),
                    value: state.selectedPaymentMethod?.name.stringValue(),
                    action: {
                        action(CreateOrderActionPaymentMethodClick())
                    }
                )
                .padding(.top, Diems.SMALL_PADDING)
                .padding(.horizontal, 16)

                if state.isPaymentMethodErrorShown {
                    Text("error_select_payment_method")
                        .bodySmall()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.error)
                        .padding(.top, 4)
                        .padding(.horizontal, 16)
                }

                VStack(spacing: 0) {
                    if state.showChange {
                        HStack(spacing: 0) {
                            Button(action: {
                                action(
                                    CreateOrderActionChangeWithoutChangeChecked()
                                )
                            }) {
                                FoodDeliveryCheckBox(
                                    isSelected: state.withoutChangeChecked,
                                    action: {
                                        action(
                                            CreateOrderActionChangeWithoutChangeChecked()
                                        )
                                    }
                                )

                                Text("msg_without_change")
                                    .foregroundColor(AppColor.onSurface)
                                    .bodyMedium()

                                Spacer()
                            }
                            .frame(maxWidth: .infinity)
                            .padding(.top, 8)
                            .padding(.bottom, 16)
                            .padding(.horizontal, 16)
                        }

                        if !state.withoutChangeChecked {
                            EditTextView(
                                hint: "С какой суммы подготовить сдачу?*",
                                text: $changeTextField,
                                limit: 10,
                                keyBoadrType: UIKeyboardType.numberPad,
                                errorMessage: $changeError,
                                textChanged: { _ in
                                    action(CreateOrderActionChangeChange(change: changeTextField))
                                }
                            )
                            .padding(.bottom, 8)
                            .padding(.horizontal, 16)
                        }
                    }

                    if state.additionalUtensils {
                        EditTextView(
                            hint: "Количество приборов*",
                            text: $additionalUtensilsCountField,
                            limit: 10,
                            keyBoadrType: UIKeyboardType.numberPad,
                            errorMessage: $additionalUtensilsError,
                            textChanged: { _ in
                                action(
                                    CreateOrderActionChangeAdditionalUtensils(
                                        additionalUtensilsCount: additionalUtensilsCountField
                                    )
                                )
                            }
                        )
                        .padding(.bottom, 8)
                        .padding(.horizontal, 16)
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
                    .padding(.horizontal, 16)
                }.padding(.top, 16)

                Toggle(
                    isOn: $faster.onChange(
                        { faster in
                            if faster {
                                action(CreateOrderActionAsapClick())
                            } else {
                                let date = Date.now + 60 * 60
                                action(
                                    CreateOrderActionChangeDeferredTime(
                                        time: Time(
                                            hours: Int32(
                                                calendar.component(.hour, from: date)
                                            ),
                                            minutes: Int32(
                                                calendar.component(.minute, from: date)
                                            )
                                        )
                                    )
                                )
                            }
                        }
                    )
                ) {
                    Text("Как можно скорее")
                        .bodyLarge()
                }
                .toggleStyle(.automatic)
                .padding(.top, Diems.MEDIUM_PADDING)
                .padding(.horizontal, Diems.MEDIUM_PADDING)

                if !faster {
                    DatePicker(
                        selection: $deferredTime.onChange(
                            { date in
                                action(
                                    CreateOrderActionChangeDeferredTime(
                                        time: Time(
                                            hours: Int32(
                                                calendar.component(.hour, from: date)
                                            ),
                                            minutes: Int32(
                                                calendar.component(.minute, from: date)
                                            )
                                        )
                                    )
                                )
                            }
                        ),
                        in: (Date.now + 60 * 60)...,
                        displayedComponents: .hourAndMinute
                    ) {
                        if isDelivery {
                            Text("Время доставки")
                                .bodyLarge()
                        } else {
                            Text("Время самовывоза")
                                .bodyLarge()
                        }
                    }
                    .padding(.top, Diems.SMALL_PADDING)
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                }
            }
        }
    }

    struct BottomAmountBarSuccessView: View {
        let motivation: MotivationUi?
        let discount: String?
        let deliveryCost: String?
        let oldFinalCost: String?
        let newFinalCost: String

        var body: some View {
            VStack(spacing: 0) {
                if let motivation = motivation {
                    Motivation(motivation: motivation)
                }

                if let discount = discount {
                    HStack(spacing: 0) {
                        Text("create_order_discount")
                            .bodyMedium()
                            .foregroundColor(AppColor.onSurface)

                        Spacer()

                        DiscountCard(text: discount)
                    }.padding(.top, 8)
                        .padding(.horizontal, 16)
                }

                if let deliveryCost = deliveryCost {
                    HStack(spacing: 0) {
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

                HStack(spacing: 0) {
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
}
