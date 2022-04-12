//
//  ProfileView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct ProfileView: View {
    var body: some View {
        VStack{
            
            ToolbarView(title: Strings.TITLE_PROFILE, cost: "220 R", count: "2", isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            EmptyProfileView()
            
            //LoadingProfileView()
            
            //SuccessProfileView(profileUI: ProfileUI(userUUid: "String", hasAddresses: false,lastOrderItem: OrderItem(id: UUID(),status: "PREPARING", code: "H-03", dateTime: "9 февраля 22:00")))
            
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
        .navigationBarHidden(true)

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
        ProgressView()
            .progressViewStyle(CircularProgressViewStyle(tint: Color("primary")))
            .scaleEffect(1.5)
    }
}

struct SuccessProfileView: View {
    let profileUI:ProfileUI
    
    var body: some View {
        VStack{
            
            if(profileUI.lastOrderItem != nil){
                OrderItemView(orderItem: profileUI.lastOrderItem!, destination: OrderDetailsView())
            }
            
            NavigationCardView(icon: "gearshape", label: Strings.TITLE_PROFILE_SETTINGS, destination: FeedbackView())
            
            if(profileUI.hasAddresses){
                NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_YOUR_ADDRESSES, destination: AboutAppView())
            }else{
                NavigationCardView(icon: "plus", label: Strings.TITLE_PROFILE_ADD_ADDRESSES, destination: AboutAppView())
            }
            
            NavigationCardView(icon: "clock.arrow.circlepath", label: Strings.TITLE_PROFILE_ORDER_HISTORY, destination: FeedbackView())
            
            NavigationCardView(icon: "dollarsign.circle", label: Strings.TITLE_PROFILE_PAYMENT, destination: AboutAppView())
            
            NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
            
            NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
            
            Spacer()
            
        }.padding(Diems.MEDIUM_PADDING)
    }
}
