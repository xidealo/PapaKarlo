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

    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_PROFILE, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count, isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            if(viewModel.profileViewState.isLoading){
                LoadingProfileView()
            }else{
                if viewModel.profileViewState.isAuthorize{
                    SuccessProfileView(profileViewState:  viewModel.profileViewState)
                }else{
                    EmptyProfileView()
                }
            }
            BottomBarView(isSelected: 2)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background")).hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.fetchProfile()
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}


struct EmptyProfileView: View {
    var body: some View {
        VStack{
            NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
            
            NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
            
            Spacer()
            
            DefaultImage(imageName: "NotLoginnedProfile")
            
            Text(Strings.MSG_PROFILE_NO_PROFILE).multilineTextAlignment(.center)
            
            Spacer()
            
            NavigationLink(
                destination:LoginView()
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
    
    var body: some View {
        VStack{
            if(profileViewState.lastOrder != nil){
                OrderItemView(orderItem: profileViewState.lastOrder!, destination: OrderDetailsView(orderUuid: profileViewState.lastOrder!.id))
            }
            
            NavigationCardView(icon: "gearshape", label: Strings.TITLE_PROFILE_SETTINGS, destination: SettingsView())
            
            if(profileViewState.hasAddresses){
                NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_YOUR_ADDRESSES, destination: UserAddressListView(isClickable: false))
            }else{
                NavigationCardView(icon: "plus", label: Strings.TITLE_PROFILE_ADD_ADDRESSES, destination: CreateAddressView())
            }
            
            NavigationCardView(icon: "clock.arrow.circlepath", label: Strings.TITLE_PROFILE_ORDER_HISTORY, destination: OrderListView())
            
            NavigationCardView(icon: "dollarsign.circle", label: Strings.TITLE_PROFILE_PAYMENT, destination: PaymentView())
            
            NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
            
            NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
            
            Spacer()
            
        }.padding(Diems.MEDIUM_PADDING)
    }
}
