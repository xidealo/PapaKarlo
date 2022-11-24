//
//  SettingsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct SettingsView: View {
    
    @ObservedObject private var viewModel = SettingsViewModel()
    @State private var showingAlert = false
    
    private let authManager = AuthManager()
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: Strings.TITLE_SETTINGS,
                cost: "",
                count: "2",
                isCartVisible: false,
                logout : {
                    logout()
                },
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                TextCard(placeHolder: Strings.HINT_SETTINGS_PHONE, text: viewModel.settingsViewState.phone)
                
                //TODO(Add Email in next Version)
                
                NavigationTextCard(placeHolder: Strings.HINT_SETTINGS_CITY, text: viewModel.settingsViewState.city, destination:ChangeCityView())
                    .padding(.top, Diems.SMALL_PADDING)
                Spacer()
                Button(action: {
                    showingAlert = true
                }) {
                    Text(Strings.ACTION_SETTINGS_REMOVE_ACCOUNT).frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("errorColor"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }.alert("Вы уверены, что хотите удалить аккаунт?", isPresented: $showingAlert) {
                    Button("Да") {
                        viewModel.disableUser { isSuccess in
                            if isSuccess{
                                DispatchQueue.main.async {
                                    logout()
                                }
                            }
                        }
                    }
                    Button("Нет", role: .cancel) { }
                }
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
    }
    
    func logout(){
        authManager.logout()
        iosComponent.provideIUserInteractor().clearUserCache { err in
            self.mode.wrappedValue.dismiss()
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
