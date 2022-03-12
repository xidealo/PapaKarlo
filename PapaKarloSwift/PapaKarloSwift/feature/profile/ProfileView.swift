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
            //EmptyProfileView()
           
            //LoadingProfileView()
            
            VStack{
                NavigationCardView(icon: "gearshape", label: Strings.TITLE_PROFILE_SETTINGS, destination: FeedbackView())
                
                NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_YOUR_ADDRESSES, destination: AboutAppView())
                
                NavigationCardView(icon: "clock.arrow.circlepath", label: Strings.TITLE_PROFILE_ORDER_HISTORY, destination: FeedbackView())
                
                NavigationCardView(icon: "dollarsign.circle", label: Strings.TITLE_PROFILE_PAYMENT, destination: AboutAppView())
                
                NavigationCardView(icon: "star", label: Strings.TITLE_PROFILE_FEEDBACK, destination: FeedbackView())
                
                NavigationCardView(icon: "info.circle", label: Strings.TITLE_PROFILE_ABOUT_APP, destination: AboutAppView())
                
                Spacer()
                
            }.padding(Diems.MEDIUM_PADDING)
          
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
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
            
            Image("NotLoginnedProfile")
            
            Text(Strings.MSG_PROFILE_NO_PROFILE).multilineTextAlignment(.center)
            
            Spacer()
            
            NavigationLink(
                destination:SelectCityView()
            ){
                Text(Strings.ACTION_PROFILE_LOGIN).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(8)
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
