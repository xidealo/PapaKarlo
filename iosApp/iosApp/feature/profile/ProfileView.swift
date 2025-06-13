//
//  ProfileView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import shared
import SwiftUI

struct ProfileView: View {
    @State var profileState = ProfileState(
        lastOrder: nil,
        state: ProfileState.State.loading,
        cartCostAndCount: nil,
        paymentMethodList: [],
        linkList: [],
        eventList: []
    )

    var viewModel = ProfileViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        getLastOrderUseCase: iosComponent.provideGetLastOrderUseCase(), observeLastOrderUseCase: iosComponent.provideObserveLastOrderUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase(),
        getPaymentMethodListUseCase: iosComponent.provideGetPaymentMethodListUseCase(),
        getLinkListUseCase: iosComponent.provideGetLinkListUseCase()
    )

    @Binding var showOrderCreated: Bool
    @Binding var showCreatedAddress: Bool
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @State var isActive: Bool = false
    @State var listener: Closeable? = nil

    @Environment(\.scenePhase) var scenePhase

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: "title_profile",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            switch profileState.state {
            case ProfileState.State.loading: LoadingProfileView()
            case ProfileState.State.authorized: SuccessProfileView(
                    profileViewState: profileState,
                    showOrderCreated: $showOrderCreated,
                    showCreatedAddress: $showCreatedAddress
                )
            case ProfileState.State.unauthorized: EmptyProfileView(
                    isActive: $isActive,
                    paymentMethodList: profileState.paymentMethodList,
                    linkList: profileState.linkList
                )
            default: EmptyView()
            }

            NavigationLink(
                destination: LoginView(rootIsActive: self.$isActive, isGoToCreateOrder: .constant(false)),
                isActive: self.$isActive
            ) {
                EmptyView()
            }
            .isDetailLink(false)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
        .onAppear {
            subscribe()
        }
        .onDisappear {
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

    func subscribe() {
        viewModel.update()
        viewModel.observeLastOrder()
        listener = viewModel.profileState.watch { profileStateVM in
            if let notNullprofileStateVM = profileStateVM {
                profileState = notNullprofileStateVM
            }
        }
    }

    func unsubscribe() {
        viewModel.stopLastOrderObservation()
        listener?.close()
        listener = nil
    }
}

struct EmptyProfileView: View {
    @Binding var isActive: Bool
    var paymentMethodList: [PaymentMethod]
    var linkList: [shared.Link]

    var body: some View {
        VStack(spacing: 0) {
            NavigationIconCardWithDivider(
                icon: "CafesIcon",
                label: "Рестораны",
                destination: CafeListView(),
                isSystem: false
            )
            
            NavigationIconCardWithDivider(
                icon: "ic_payment",
                label: "Оплата",
                destination: PaymentView(paymentMethodList: paymentMethodList),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "ic_star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView(linkList: linkList),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "ic_info",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView(),
                isSystem: false
            )

            Spacer()

            EmptyWithIconView(
                imageName: "ProfileIcon",
                title: "loginTitleProfile",
                secondText: "loginSecondProfile"
            )
            .padding(.horizontal, Diems.MEDIUM_PADDING)

            Spacer()

            Button(
                action: {
                    isActive = true
                }, label: {
                    ButtonText(text: Strings.ACTION_PROFILE_LOGIN)
                }
            )
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.bottom, 16)
        }
    }
}

struct LoadingProfileView: View {
    var body: some View {
        LoadingView()
    }
}

struct SuccessProfileView: View {
    let profileViewState: ProfileState
    @Binding var showOrderCreated: Bool
    @Binding var showCreatedAddress: Bool

    var body: some View {
        VStack(spacing: 0) {
            if let lastOrder = profileViewState.lastOrder {
                OrderProfileView(
                    lightOrder: lastOrder,
                    destination: OrderDetailsView(orderUuid: lastOrder.uuid)
                )
            }

            NavigationIconCardWithDivider(
                icon: "ic_settings",
                label: Strings.TITLE_PROFILE_SETTINGS,
                destination: SettingsView(),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "AddressIcon",
                label: Strings.TITLE_PROFILE_MY_ADDRESSES,
                destination: UserAddressListView(
                    isClickable: false,
                    closedCallback: {
                        // stub dont need
                    }
                ),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "ic_history",
                label: Strings.TITLE_PROFILE_MY_ORDERS,
                destination: OrderListView(),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "CafesIcon",
                label: "Рестораны",
                destination: CafeListView(),
                isSystem: false
            )
            
            NavigationIconCardWithDivider(
                icon: "ic_payment",
                label: Strings.TITLE_PROFILE_PAYMENT,
                destination: PaymentView(paymentMethodList: profileViewState.paymentMethodList),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "ic_star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView(linkList: profileViewState.linkList),
                isSystem: false
            )

            NavigationIconCardWithDivider(
                icon: "ic_info",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView(),
                isSystem: false
            )

            Spacer()
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Код заказа: \(profileViewState.lastOrder?.code ?? "")"),
                show: $showOrderCreated,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $showOrderCreated
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес добавлен"),
                show: $showCreatedAddress,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $showCreatedAddress
        )
    }
}

struct OrderProfileView<Content: View>: View {
    let lightOrder: LightOrder
    let destination: Content

    var body: some View {
        NavigationLink(
            destination: destination
        ) {
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    Text(lightOrder.code)
                        .foregroundColor(AppColor.onSurface)
                        .titleMedium(weight: .bold)

                    OrderChip(orderStatus: lightOrder.status)
                        .padding(.leading, Diems.SMALL_PADDING)

                    Spacer()

                    Text(dateUtil.getDateTimeString(dateTime: lightOrder.dateTime))
                        .bodySmall()
                        .foregroundColor(AppColor.onSurfaceVariant)
                }.frame(maxWidth: .infinity)
                    .padding(16)
                    .background(AppColor.surface)

                FoodDeliveryDivider()
                    .padding(.horizontal, 16)
            }
        }
    }
}
