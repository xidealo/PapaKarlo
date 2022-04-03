//
//  ContainerView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct ContainerView: View {
    var body: some View {
        TabView{
            CafeListView().tabItem {
                Text(Strings.TITLE_BOTTOM_NAVIGATION_CAFES)
            }
            MenuView().tabItem {
                Text(Strings.TITILE_BOTTOM_NAVIGATION_MENU)
            }
            ProfileView().tabItem {
                Text(Strings.TITLE_BOTTOM_NAVIGATION_PROFILE)
            }
        }
    }
}

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView()
    }
}
