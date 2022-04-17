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
                Image(systemName:"mappin")
                    Text(Strings.TITLE_BOTTOM_NAVIGATION_CAFES)
            }.tag(0).navigationTitle(
                Text(Strings.TITLE_CAFE_LIST)
            )
            MenuView().tabItem {
                Text(Strings.TITILE_BOTTOM_NAVIGATION_MENU)
                Image(systemName:"list.dash")
            }.tag(1)
            ProfileView().tabItem {
                Image(systemName: "person.crop.circle")
                Text(Strings.TITLE_BOTTOM_NAVIGATION_PROFILE)
            }.tag(2)
        }.accentColor(Color("primary")).navigationBarHidden(true)
    }
}

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView()
    }
}
