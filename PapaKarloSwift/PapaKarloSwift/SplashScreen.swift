//
//  ContentView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import SwiftUI
import shared

struct SplashView: View {
    var body: some View {
        NavigationView{
            NavigationLink(
                destination:SelectCityView()
            ){
                
            }
        }.navigationBarBackButtonHidden(true)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
