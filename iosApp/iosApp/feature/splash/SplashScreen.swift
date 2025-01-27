//
//  ContentView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.02.2022.
//

import SwiftUI
import shared

struct SplashView: View, SharedLifecycle {
    
    @State var topMessage : LocalizedStringKey? = nil
    
    @State var viewModel: SplashViewModel = SplashViewModel(
        checkUpdateUseCase: iosComponent.provideCheckUpdateUseCase(),
        cityInteractor: iosComponent.provideCityInteractor(),
        getIsOneCityUseCase: iosComponent.provideCheckOneCityUseCase(),
        saveOneCityUseCase: iosComponent.provideSaveOneCityUseCase()
    )
    
    @State var eventsListener: Closeable?
    
    //Navigation
    @State var openSelectCity: Bool = false
    @State var openMainMenu: Bool = false
    @State var openUpdateScreen: Bool = false
    // ---
    
    init(){
        UINavigationBar.setAnimationsEnabled(false)
    }
    
    var body: some View {
        VStack(spacing:0){
            if(openSelectCity){
                if let topMessage = topMessage {
                    Text(topMessage)
                        .bodyMedium()
                        .foregroundColor(AppColor.onStatus)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 4)
                        .padding(.horizontal, 16)
                        .background(AppColor.warning)
                }
                
                NavigationView{
                    NavigationLink(
                        destination: SelectCityView(),
                        isActive: $openSelectCity
                    ){
                        LoadingView()
                    }.isDetailLink(false)
                }
            }
            
            if(openMainMenu){
            if let topMessage = topMessage {
                    Text(topMessage)
                        .bodyMedium()
                        .foregroundColor(AppColor.onStatus)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 4)
                        .padding(.horizontal, 16)
                        .background(AppColor.warning)
                }
                NavigationView{
                    NavigationLink(
                        destination: ContainerView(selection: MainContainerState.menu),
                        isActive: $openMainMenu
                    ){
                        LoadingView()
                    }.isDetailLink(false)
                }
            }
            
            
            if(openUpdateScreen){
                NavigationView{
                    NavigationLink(
                        destination: UpdateView(),
                        isActive: $openUpdateScreen
                    ){
                        LoadingView()
                    }.isDetailLink(false)
                }
            }
            
        }.onAppear(perform: {
            viewModel.onAction(action: SplashActionInit())
            eventsSubscribe()
            iosComponent.provideGetWorkInfoUseCase().invoke { workInfo, err in
                
                let workInfoType = workInfo?.workInfoType ?? WorkInfo.WorkInfoType.deliveryAndPickup
                
                switch(workInfoType){
                case WorkInfo.WorkInfoType.deliveryAndPickup:
                    print("deliveryAndPickup")
                    topMessage = nil
                case WorkInfo.WorkInfoType.delivery:
                    topMessage = "warning_no_only_delivery"
                case WorkInfo.WorkInfoType.pickup:
                    topMessage = "warning_no_only_pickup"
                case WorkInfo.WorkInfoType.closed:
                    topMessage = "warning_no_order_available"

                default:
                    print("workInfoType \(workInfoType)")
                    topMessage = nil
                }
            }
        })
        .onDisappear(){
            unsubscribe()
        }
    }
    
    func eventsSubscribe() {
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let splashEvents = events as? [SplashEvent] ?? []
                splashEvents.forEach { event in
                    switch(event){
                    case is SplashEventNavigateToUpdateEvent :
                        openUpdateScreen = true
                    case is SplashEventNavigateToMenuEvent :
                        openMainMenu = true
                    case is SplashEventNavigateToSelectCityEvent:
                        openSelectCity = true
                    default:
                        print("def")
                    }
                }
                
                if !splashEvents.isEmpty {
                    viewModel.consumeEvents(events: splashEvents)
                }
            }
        })
    }
    
    func unsubscribe() {
        eventsListener?.close()
        eventsListener = nil
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
