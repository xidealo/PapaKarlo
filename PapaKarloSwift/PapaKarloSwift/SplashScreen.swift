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
                destination:SelectCityView(),
                isActive: .constant(true)
            ){
                
            }
        }.navigationBarBackButtonHidden(true)    .navigationBarHidden(true)
            .navigationBarTitle("", displayMode: .inline)
            .navigationViewStyle(StackNavigationViewStyle())
            .statusBar(hidden: true)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
