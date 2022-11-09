//
//  SettingsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct SettingsView: View {
    
    @ObservedObject private var viewModel = SettingsViewModel()

    var body: some View {
        VStack(spacing:0){
            ToolbarView(title: Strings.TITLE_SETTINGS,  cost: "", count: "2", isShowBackArrow: true, isCartVisible: false, isLogoutVisible: true)
            
            VStack(spacing:0){
                TextCard(placeHolder: Strings.HINT_SETTINGS_PHONE, text: viewModel.settingsViewState.phone)
//                TODO(Add Email in next Version)
//                if viewModel.settingsViewState.email == nil{
//                    NavigationCardView(icon: "", label: Strings.HINT_SETTINGS_EMAIL, destination: ChangeCityView())
//                }else{
//                    NavigationTextCard(placeHolder: Strings.HINT_SETTINGS_EMAIL, text: viewModel.settingsViewState.email!, destination:ChangeCityView())
//                }
           
                NavigationTextCard(placeHolder: Strings.HINT_SETTINGS_CITY, text: viewModel.settingsViewState.city, destination:ChangeCityView())
                    .padding(.top, Diems.SMALL_PADDING)
                Spacer()
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()

    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
