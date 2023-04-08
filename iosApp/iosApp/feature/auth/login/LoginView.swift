//
//  LoginView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI
import Combine

let auth = AuthManager()

struct LoginView: View {
    
    @State private var phone:String = "+7"
    
    @Binding var rootIsActive:Bool
    @Binding var isGoToCreateOrder:Bool
    @State var goToConfirm:Bool = false
    
    @State var hasError:Bool = false
    @ObservedObject private var viewModel : LoginViewModel = LoginViewModel(auth: auth)
    
    var body: some View {
        VStack(spacing:0){
            if(viewModel.loginViewState.isLoading){
                LoadingView()
            }else{
                NavigationLink(
                    destination:ConfirmView(
                        auth: auth,
                        phone: phone,
                        rootIsActive: self.$rootIsActive,
                        isGoToCreateOrder: $isGoToCreateOrder
                    ),
                    isActive: $goToConfirm
                ){
                    EmptyView()
                }
                
                LoginViewSuccessView(phone: $phone,hasError: $hasError, viewModel: viewModel)
            }
        }
        .onReceive(viewModel.$loginViewState, perform: { loginViewState in
            loginViewState.actionList.forEach { action in
                switch(action){
                case LoginAction.hasError : hasError = true
                case LoginAction.goToConfirm : goToConfirm = true
                }
            }
            
            if !loginViewState.actionList.isEmpty{
                viewModel.clearActions()
            }
        })
    }
}

struct LoginViewSuccessView: View {
    @Binding var phone:String
    @Binding var hasError:Bool
    @ObservedObject var viewModel : LoginViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    @State var isSelected:Bool = false
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title:"",
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            
            VStack(spacing:0){
                Spacer()
                
                Image("LoginLogo")
                    .resizable()
                    .frame(width: 152, height: 120)
                
                Text(Strings.MSG_LOGIN_ENTER_PHONE)
                    .multilineTextAlignment(.center)
                    .foregroundColor(Color("onSurface"))
                    .padding(.top, Diems.MEDIUM_PADDING)
                
                EditTextView(
                    hint: Strings.HINT_LOGIN_PHONE,
                    text:$phone, limit: 18,
                    keyBoadrType: UIKeyboardType.phonePad,
                    hasError: $hasError,
                    errorMessage: "Введите номер телефона",
                    textChanged: { str in
                        
                    }
                )
                .padding(.top, Diems.SMALL_PADDING)
                .onReceive(Just(phone)) { _ in
                    minCode()
                }
                .keyboardType(.phonePad)
                
                Spacer()
                
                Button {
                    hasError = false
                    viewModel.sendCodeToPhone(phone: phone)
                } label: {
                    ButtonText(text: Strings.ACTION_LOGIN_LOGIN)
                }
            }.padding(Diems.MEDIUM_PADDING)
        }
        .background(Color("surface"))
        .hiddenNavigationBarStyle()
    }
    
    func minCode() {
        if phone.count < 2 {
            phone = String("+7")
        }else{
            phone = phone.applyPatternOnNumbers(pattern: "+# (###) ###-##-##", replacementCharacter: "#")
        }
    }
}

extension String {
    func applyPatternOnNumbers(pattern: String, replacementCharacter: Character) -> String {
        var pureNumber = self.replacingOccurrences( of: "[^0-9]", with: "", options: .regularExpression)
        for index in 0 ..< pattern.count {
            guard index < pureNumber.count else { return pureNumber }
            let stringIndex = String.Index(utf16Offset: index, in: pattern)
            let patternCharacter = pattern[stringIndex]
            guard patternCharacter != replacementCharacter else { continue }
            pureNumber.insert(patternCharacter, at: stringIndex)
        }
        return pureNumber
    }
}
