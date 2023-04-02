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
        eventList: []
    )
    
    var viewModel = ProfileViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        getLastOrderUseCase: iosComponent.provideGetLastOrderUseCase(), observeLastOrderUseCase:iosComponent.provideObserveLastOrderUseCase(),
        stopObserveOrdersUseCase: iosComponent.provideStopObserveOrdersUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase()
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
            case ProfileState.State.unauthorized : EmptyProfileView(isActive: $isActive)
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
        .background(Color("background"))
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
            if(profileStateVM != nil ){
                profileState = profileStateVM!
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
    
    var body: some View {
        VStack(spacing:0){
            NavigationCardView(
                icon: "star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView()
            )
            
            NavigationCardView(
                icon: "info.circle",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView()
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
            
            DefaultImage(imageName: "NotLoginnedProfile")
            
            BoldText(text: "Войдите в профиль")
                .padding(.top, 32)
            
            Text(
                Strings.MSG_PROFILE_NO_PROFILE
            )
            .multilineTextAlignment(.center)
            .padding(.top, Diems.SMALL_PADDING)
            
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
            
            if(profileViewState.lastOrder != nil){
                LightOrderItemView(
                    lightOrder: profileViewState.lastOrder!,
                    destination: OrderDetailsView(orderUuid: profileViewState.lastOrder!.uuid)
                )
            }
            
            NavigationCardView(
                icon: "gearshape",
                label: Strings.TITLE_PROFILE_SETTINGS,
                destination: SettingsView()
            )
            .padding(.top, Diems.SMALL_PADDING)

            NavigationCardView(
                icon: "AddressIcon",
                label: Strings.TITLE_PROFILE_MY_ADDRESSES,
                destination: UserAddressListView(isClickable: false),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "clock.arrow.circlepath",
                label: Strings.TITLE_PROFILE_MY_ORDERS,
                destination: OrderListView()
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "dollarsign.circle",
                label: Strings.TITLE_PROFILE_PAYMENT,
                destination: PaymentView()
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "star",
                label: Strings.TITLE_PROFILE_FEEDBACK,
                destination: FeedbackView()
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "info.circle",
                label: Strings.TITLE_PROFILE_ABOUT_APP,
                destination: AboutAppView()
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
            
        }.padding(Diems.MEDIUM_PADDING)
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Код заказа: \(profileViewState.lastOrder?.code ?? "")"),
                    show: $showOrderCreated,
                    backgroundColor:Color("primary"),
                    foregaroundColor: Color("onPrimary")),
                show: $showOrderCreated
            )
            .overlay(
                overlayView: ToastView(
                    toast: Toast(title: "Адрес добавлен"),
                    show: $showCreatedAddress,
                    backgroundColor:Color("primary"),
                    foregaroundColor: Color("onPrimary")),
                show: $showCreatedAddress
            )
    }
}
