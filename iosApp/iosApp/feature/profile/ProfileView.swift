//
//  ProfileView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared


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
        getLastOrderUseCase: iosComponent.provideGetLastOrderUseCase(), observeLastOrderUseCase:iosComponent.provideObserveLastOrderUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase(),
        getPaymentMethodListUseCase: iosComponent.provideGetPaymentMethodListUseCase(),
        getLinkListUseCase: iosComponent.provideGetLinkListUseCase()
    )
    
    @Binding var showOrderCreated:Bool
    @Binding var showCreatedAddress:Bool
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @State var isActive:Bool = false
    @State var listener: Closeable? = nil
    
    @Environment(\.scenePhase) var scenePhase
    
    var body: some View {
        VStack(spacing:0){
            switch(profileState.state){
            case ProfileState.State.loading : LoadingProfileView()
            case ProfileState.State.authorized : SuccessProfileView(
                profileViewState:  profileState,
                showOrderCreated: $showOrderCreated,
                showCreatedAddress: $showCreatedAddress
            )
            case ProfileState.State.unauthorized : EmptyProfileView(
                isActive: $isActive,
                paymentMethodList: profileState.paymentMethodList,
                linkList: profileState.linkList
            )
            default: EmptyView()
            }
            
            NavigationLink(
                destination:LoginView(rootIsActive: self.$isActive, isGoToCreateOrder: .constant(false)),
                isActive: self.$isActive
            ){
                EmptyView()
            }
            .isDetailLink(false)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
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
        viewModel.update()
        viewModel.observeLastOrder()
        listener = viewModel.profileState.watch { profileStateVM in
            if let notNullprofileStateVM = profileStateVM {
                profileState = notNullprofileStateVM
            }
        }
    }
    
    func unsubscribe(){
        viewModel.stopLastOrderObservation()
        listener?.close()
        listener = nil
    }
}

struct EmptyProfileView: View {
    @Binding var isActive:Bool
    var paymentMethodList : [PaymentMethod]
    var linkList: [shared.Link]
    
    var body: some View {
        VStack(spacing:0){
            NavigationCardView(
                icon: "ic_payment",
                label: "Оплата",
                destination: PaymentView(paymentMethodList:paymentMethodList),
                isSystem: false
            )
            
            NavigationCardView(
                icon: "ic_star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView(linkList: linkList),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)

            NavigationCardView(
                icon: "ic_info",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView(),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
                        
            EmptyWithIconView(
                imageName:  "ProfileIcon",
                title : "loginTitleProfile",
                secondText: "loginSecondProfile"
            )

            Spacer()
            
            Button(
                action: {
                    isActive = true
                }, label: {
                    ButtonText(text: Strings.ACTION_PROFILE_LOGIN)
                }
            )
        }.padding(Diems.MEDIUM_PADDING)
    }
}

struct LoadingProfileView: View {
    var body: some View {
        LoadingView()
    }
}

struct SuccessProfileView: View {
    let profileViewState:ProfileState
    @Binding var showOrderCreated:Bool
    @Binding var showCreatedAddress:Bool
    
    var body: some View {
        VStack(spacing:0){
            if let lastOrder =  profileViewState.lastOrder {
                LightOrderItemView(
                    lightOrder: lastOrder,
                    destination: OrderDetailsView(orderUuid: lastOrder.uuid)
                )
            }
            
            NavigationCardView(
                icon: "ic_settings",
                label: Strings.TITLE_PROFILE_SETTINGS,
                destination: SettingsView(),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)

            NavigationCardView(
                icon: "AddressIcon",
                label: Strings.TITLE_PROFILE_MY_ADDRESSES,
                destination: UserAddressListView(
                    isClickable: false,
                    closedCallback: {
                        //stub dont need
                    }
                ),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "ic_history",
                label: Strings.TITLE_PROFILE_MY_ORDERS,
                destination: OrderListView(),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "ic_payment",
                label: Strings.TITLE_PROFILE_PAYMENT,
                destination: PaymentView(paymentMethodList:profileViewState.paymentMethodList),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "ic_star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView(linkList: profileViewState.linkList),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "ic_info",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView(),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
            
        }.padding(Diems.MEDIUM_PADDING)
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
                    backgroundColor:AppColor.primary,
                    foregroundColor: AppColor.onPrimary),
                show: $showCreatedAddress
            )
    }
}
