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
        eventList: []
    )
    
    var viewModel = ProfileViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        observeLastOrderUseCase:iosComponent.provideObserveLastOrderUseCase(),
        stopObserveLastOrderUseCase: iosComponent.provideStopObserveLastOrderUseCase()
    )
    
    @Binding var showOrderCreated:Bool
    @Binding var showCreatedAddress:Bool
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @State var isActive:Bool = false
    
    var body: some View {
        VStack(spacing:0){
            switch(profileState.state){
            case ProfileState.State.loading : LoadingProfileView()
            case ProfileState.State.authorized : SuccessProfileView(profileViewState:  profileState, showOrderCreated: $showOrderCreated, showCreatedAddress: $showCreatedAddress)
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
            viewModel.update()
            viewModel.observeLastOrder()
            viewModel.profileState.watch { profileStateVM in
                if(profileStateVM != nil ){
                    profileState = profileStateVM!
                }
                // work with actions
            }
        }
        .onDisappear(){
            viewModel.stopLastOrderObservation()
        }
        
    }
}

struct EmptyProfileView: View {
    @Binding var isActive:Bool
    
    var body: some View {
        VStack(spacing:0){
            NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
            
            NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
                .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
            
            DefaultImage(imageName: "NotLoginnedProfile")
            
            Text(Strings.MSG_PROFILE_NO_PROFILE).multilineTextAlignment(.center)
                .padding(.top, Diems.SMALL_PADDING)
            
            Spacer()
            
            Button(
                action: {
                    isActive = true
                }, label: {
                    Text(Strings.ACTION_PROFILE_LOGIN).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
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
                LightOrderItemView(lightOrder: profileViewState.lastOrder!, destination: OrderDetailsView(orderUuid: profileViewState.lastOrder!.uuid))
            }
            
            NavigationCardView(icon: "gearshape", label: Strings.TITLE_PROFILE_SETTINGS, destination: SettingsView())
                .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(
                icon: "AddressIcon",
                label: Strings.TITLE_PROFILE_YOUR_ADDRESSES,
                destination: UserAddressListView(isClickable: false),
                isSystem: false
            )
            .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(icon: "clock.arrow.circlepath", label: Strings.TITLE_PROFILE_ORDER_HISTORY, destination: OrderListView())
                .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(icon: "dollarsign.circle", label: Strings.TITLE_PROFILE_PAYMENT, destination: PaymentView())
                .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
                .padding(.top, Diems.SMALL_PADDING)
            
            NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
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
