//
//  SettingsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct SettingsView: View {
    
    let settingsUI:SettingsUI
    var body: some View {
        VStack{
            TextCard(placeHolder: Strings.HINT_SETTINGS_PHONE, text: settingsUI.phone)
            if settingsUI.email == nil{
                NavigationCardView(icon: "", label: Strings.HINT_SETTINGS_EMAIL, destination: ChangeCityView())
            }else{
                NavigationTextCard(placeHolder: Strings.HINT_SETTINGS_EMAIL, text: settingsUI.email!, destination:ChangeCityView())
            }
       
            NavigationTextCard(placeHolder: Strings.HINT_SETTINGS_CITY, text: settingsUI.city, destination:ChangeCityView())
            Spacer()
        }.padding(Diems.MEDIUM_PADDING).frame(maxWidth:.infinity, maxHeight: .infinity).background(Color("background")).navigationTitle(Text(Strings.TITLE_SETTINGS)).navigationBarItems(leading: Image("chevron.forward.square"))
        
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView(settingsUI:SettingsUI(phone: "899692241", email: "shavl.mark@yandex.ru", city: "Moscow"))
    }
}
