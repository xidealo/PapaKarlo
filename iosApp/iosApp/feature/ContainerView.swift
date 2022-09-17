//
//  ContainerView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct ContainerView: View {
    @State var selection:Int
    
    init(selection:Int = 1) {
        UITabBar.appearance().backgroundColor = UIColor(Color("surface"))
        self.selection = selection
    }
    
    var body: some View {
        TabView(selection:$selection){
            CafeListView().tabItem {
                Image(systemName:"mappin")
                Text(Strings.TITLE_BOTTOM_NAVIGATION_CAFES)
            }.tag(0)
            MenuView().tabItem {
                Text(Strings.TITILE_BOTTOM_NAVIGATION_MENU)
                Image(systemName:"list.dash")
            }.tag(1)
            ProfileView(show: false).tabItem {
                Image(systemName: "person.crop.circle")
                Text(Strings.TITLE_BOTTOM_NAVIGATION_PROFILE)
            }.tag(2)
        }.accentColor(Color("primary")).hiddenNavigationBarStyle()
    }
}

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView()
    }
}
