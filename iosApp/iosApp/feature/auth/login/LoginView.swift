//
//  LoginView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI
import Combine

struct LoginView: View {
    
    @State private var phone:String = "+7"
    let auth = AuthManager()
    private let isGoToProfile:Bool
    
    @ObservedObject private var viewModel : LoginViewModel
    
    init(isGoToProfile:Bool){
        self.isGoToProfile = isGoToProfile
        viewModel = LoginViewModel(auth: auth)
    }
    
    var body: some View {
        VStack{
            if(viewModel.loginViewState.isLoading){
                LoadingView()
            }else{
                NavigationLink(
                    destination:ConfirmView(auth: auth, phone: phone, isGoToProfile: isGoToProfile),
                    isActive: $viewModel.loginViewState.isGoToMenu
                ){
                    EmptyView()
                }
                
                LoginViewSuccessView(phone: $phone, viewModel: viewModel)
            }
        }.onDisappear(){
            viewModel.loginViewState.isGoToMenu = false
        }
    }
}

struct LoginViewSuccessView: View {
    @Binding var phone:String
    @ObservedObject var viewModel : LoginViewModel
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(title:"", cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
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
                    text:$phone, limit: 17,
                    keyBoadrType: UIKeyboardType.phonePad,
                    hasError: $viewModel.loginViewState.hasError,
                    errorMessage: "Введите номер телефона"
                )
                .padding(.top, Diems.SMALL_PADDING)
                .onReceive(Just(phone)) { _ in
                    minCode()
                }
                .keyboardType(.phonePad)
                
                Spacer()
                Button {
                    viewModel.sendCodeToPhone(phone: phone)
                } label: {
                    Text(Strings.ACTION_LOGIN_LOGIN)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
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
            phone = phone.applyPatternOnNumbers(pattern: "+# (###) ###-####", replacementCharacter: "#")
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

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(isGoToProfile: false)
    }
}
