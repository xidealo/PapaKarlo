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
            
            VStack{
                NavigationCardView(icon: "star", label: "Отзывы")
                
                NavigationCardView(icon: "info.circle", label: "О приложении")
                
                Image("NotLoginnedProfile")
                
                Text("В профиле будут отбражаться бонусны, адреса и история ваших заказов").multilineTextAlignment(.center)
                
                NaviGaLi
            }.padding(Diems.MEDIUM_PADDING)
           

            
        }.frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background"))
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
