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
    
    init(){
        UINavigationBar.setAnimationsEnabled(false)
    }
    
    var body: some View {
        HStack(spacing:0){
            switch viewModel.splashViewState.splashState {
            case .isGoSelectCity:NavigationView{
                NavigationLink(
                    destination:SelectCityView(),
                    isActive: .constant(true)
                ){
                    EmptyView()
                }
            }
            case .isGoMenu: NavigationView{
                NavigationLink(
                    destination:ContainerView(selection: 1),
                    isActive: .constant(true)
                ){
                    EmptyView()
                }
            }
            default : EmptyView()
            }
        }
    }
}

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
            .navigationBarHidden(true)
            .navigationBarBackButtonHidden(true)
            .navigationBarTitle("", displayMode: .inline)
            .preferredColorScheme(.light)
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
