//
//  ContentView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import SwiftUI
import shared

struct SplashView: View {
    
    @State var isNavigationBarHidden: Bool = true
    
    @ObservedObject private var viewModel = SplashViewModel()
    
    var body: some View {
        NavigationView{
            switch viewModel.splashViewState.splashState {
            case .isGoSelectCity:
                NavigationLink(
                    destination:SelectCityView(),
                    isActive: .constant(true)
                ){
                    
                }
            case .isGoMenu:
                NavigationLink(
                    destination:ContainerView(),
                    isActive: .constant(true)
                ){
                    
                }
                
            default : Text("")
            }
        }.navigationBarBackButtonHidden(true)
            .navigationBarHidden(self.isNavigationBarHidden)
            .navigationBarTitle("", displayMode: .inline)
            .navigationViewStyle(StackNavigationViewStyle())
            .statusBar(hidden: true)
        
    }
}

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
            .navigationBarTitle("", displayMode: .inline)
            .navigationBarHidden(true)
            .navigationBarBackButtonHidden(true)
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
