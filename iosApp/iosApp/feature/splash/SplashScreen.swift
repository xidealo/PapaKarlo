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
    @State var showOrderNotAvailable = false
    
    init(){
        UINavigationBar.setAnimationsEnabled(false)
    }
    
    var body: some View {
        VStack(spacing:0){
            if(showOrderNotAvailable){
                Text("warning_no_order_available")
                    .bodyMedium()
                    .foregroundColor(AppColor.onStatus)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 4)
                    .padding(.horizontal, 16)
                    .background(AppColor.warning)
            }
    
            switch viewModel.splashViewState.splashState {
            case .isGoSelectCity:NavigationView{
                NavigationLink(
                    destination:SelectCityView(),
                    isActive: .constant(true)
                ){
                    EmptyView()
                }.isDetailLink(false)

            }
            case .isGoMenu: NavigationView{
                NavigationLink(
                    destination:ContainerView(selection: MainContainerState.menu),
                    isActive: .constant(true)
                ){
                    EmptyView()
                }.isDetailLink(false)
            }
            default : EmptyView()
            }
        }.onAppear(perform: {
            iosComponent.provideIsOrderAvailableUseCase().invoke { isAvailable, err in
                if let isAvailable = isAvailable{
                    showOrderNotAvailable = !(isAvailable as! Bool)
                }
            }
        })
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
struct FlatLinkStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
    }
}

extension Binding {
    func onChange(_ handler: @escaping (Value) -> Void) -> Binding<Value> {
        Binding(
            get: { self.wrappedValue },
            set: { newValue in
                self.wrappedValue = newValue
                handler(newValue)
            }
        )
    }
}
