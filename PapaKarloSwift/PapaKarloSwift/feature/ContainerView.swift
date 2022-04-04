//
//  ContainerView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct ContainerView: View {

    @State var selection:Int = 1
    
    var body: some View {
        TabView(selection:$selection){
            CafeListView().tabItem {
                VStack{
                    Text(Strings.TITLE_BOTTOM_NAVIGATION_CAFES)
                    Image("ic_cafes")
                }
            }.tag(0).navigationTitle(
                Text(Strings.TITLE_CAFE_LIST)
            )
            MenuView().tabItem {
                Text(Strings.TITILE_BOTTOM_NAVIGATION_MENU)
                Image("ic_menu")
            }.tag(1)
            ProfileView().tabItem {
                Text(Strings.TITLE_BOTTOM_NAVIGATION_PROFILE)
                Image("ic_profile")
            }.tag(2)
        }.accentColor(Color("primary"))
    }
}

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView()
    }
}
