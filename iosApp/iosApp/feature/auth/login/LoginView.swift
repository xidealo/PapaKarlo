//
//  LoginView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI

struct LoginView: View {
    
    @State private var phone:String = ""
    let auth = AuthManager()

    @ObservedObject private var viewModel : LoginViewModel
    
    init(){
        viewModel = LoginViewModel(auth: auth)
    }
    
    var body: some View {
        if(viewModel.loginViewState.isLoading){
            LoadingView().navigationBarHidden(true)
        }else{
            if(viewModel.loginViewState.isGoToMenu){
                NavigationLink(
                    destination:ConfirmView(auth: auth),
                    isActive: .constant(true)
                ){
                    LoginViewSuccessView(phone: $phone, viewModel: viewModel)
                }
            }else{
                LoginViewSuccessView(phone: $phone, viewModel: viewModel)
            }
            
        }
    }
}

struct LoginViewSuccessView: View {
    @Binding var phone:String
    @ObservedObject var viewModel : LoginViewModel
    
    var body: some View {
        
        VStack{
            ToolbarView(title:"", cost: "220 R", count: "2",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            Spacer()
            
            Image("LoginLogo").resizable().frame(width: 152, height: 120)
            Text(Strings.MSG_LOGIN_ENTER_PHONE).multilineTextAlignment(.center)
            
            EditTextView(hint: Strings.HINT_LOGIN_PHONE, text:$phone)
            
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
            .navigationBarHidden(true)
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
