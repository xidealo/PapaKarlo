//
//  ProfileView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI
import shared

struct ProfileView: View {
    
    @StateObject private var viewModel = viewModelStore.getProfileViewModelViewModel()
    @State var showOrderCreated:Bool
    @State var showCreatedAddress:Bool = false
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(title: Strings.TITLE_PROFILE, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count, isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            switch(viewModel.profileViewState.profieState){
            case ProfileState.loading : LoadingProfileView()
            case ProfileState.success : SuccessProfileView(profileViewState:  viewModel.profileViewState, showOrderCreated: $showOrderCreated, showCreatedAddress: $showCreatedAddress)
            case ProfileState.notAuthorize : EmptyProfileView()
            }
            BottomBarView(isSelected: 2)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.fetchProfile()
            viewModel.subscribeOnOrders()
            viewModel.observeLastOrder()
        }
        .onDisappear(){
            viewModel.unsubscribeFromOrders()
        }
    }
}

struct EmptyProfileView: View {
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
            
            NavigationLink(
                destination:LoginView(isGoToProfile: true)
            ){
                Text(Strings.ACTION_PROFILE_LOGIN).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }
            
        }.padding(Diems.MEDIUM_PADDING)
    }
}

struct LoadingProfileView: View {
    var body: some View {
        VStack{
            LoadingView()
        }
    }
}

struct SuccessProfileView: View {
    let profileViewState:ProfileViewState
    @Binding var showOrderCreated:Bool
    @Binding var showCreatedAddress:Bool
    
    var body: some View {
        VStack(spacing:0){
            if(profileViewState.lastOrder != nil){
                OrderItemView(orderItem: profileViewState.lastOrder!, destination: OrderDetailsView(orderUuid: profileViewState.lastOrder!.id))
            }
            
            NavigationCardView(icon: "gearshape", label: Strings.TITLE_PROFILE_SETTINGS, destination: SettingsView())
                .padding(.top, Diems.SMALL_PADDING)
            
            if(profileViewState.hasAddresses){
                NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_YOUR_ADDRESSES, destination: UserAddressListView(isClickable: false))
                    .padding(.top, Diems.SMALL_PADDING)
            }else{
                NavigationCardView(icon: "plus", label: Strings.TITLE_PROFILE_ADD_ADDRESSES, destination: CreateAddressView(show: $showCreatedAddress))
                    .padding(.top, Diems.SMALL_PADDING)

            }
            
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
