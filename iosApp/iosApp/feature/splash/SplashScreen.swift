//
//  ContentView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import SwiftUI
import shared

struct SplashView: View {
    
    @ObservedObject private var viewModel = SplashViewModel()
    
    var body: some View {
        HStack{
            NavigationView{
                switch viewModel.splashViewState.splashState {
                case .isGoSelectCity:
                    NavigationLink(
                        destination:SelectCityView(),
                        isActive: .constant(true)
                    ){
                        EmptyView()
                    }
                case .isGoMenu:
                    NavigationLink(
                        destination:ContainerView(),
                        isActive: .constant(true)
                    ){
                        EmptyView()
                    }
                    
                default : Text("")
                }
            }.navigationBarTitle("", displayMode: .inline)
                .navigationBarHidden(true)
                .navigationBarBackButtonHidden(true)
                .navigationViewStyle(StackNavigationViewStyle())
                .statusBar(hidden: true)
        }
    }
}

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
            .navigationBarHidden(true)
            .navigationBarBackButtonHidden(true)
            .navigationBarTitle("", displayMode: .inline)
    }
}

extension View {
    func hiddenNavigationBarStyle() -> some View {
        modifier( HiddenNavigationBar() )
    }
}

extension String {
    func replace(string:String, replacement:String) -> String {
        return self.replacingOccurrences(of: string, with: replacement, options: NSString.CompareOptions.literal, range: nil)
    }
    
    func removeWhitespace() -> String {
        return self.replace(string: " ", replacement: "")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
