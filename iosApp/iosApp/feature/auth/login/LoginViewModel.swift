//
//  LoginViewModel.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 07.07.2022.
//

import Foundation
import shared


class LoginViewModel:ObservableObject {
    
    @Published var loginViewState:LoginViewState = LoginViewState(
        isLoading: false,
        isGoToMenu: false,
        hasError: false
    )
    let auth : AuthManager
    
    init(auth:AuthManager){
        self.auth = auth
    }
    
    func sendCodeToPhone(phone:String){
        if(phone.count < 17){
            loginViewState  = LoginViewState(isLoading: false, isGoToMenu: false, hasError: true)
            return
        }
        
        let formattedPhone = phone.replace(string: "(", replacement: "")
            .replace(string: ")", replacement: "")
            .replace(string: "-", replacement: "")
            .replace(string: " ", replacement: "")
        
        loginViewState  = LoginViewState(isLoading: true, isGoToMenu: false, hasError: false)
        print("formatted phone = \(formattedPhone)")
        auth.startAuth(phoneNumber: formattedPhone) { result in
            self.loginViewState  = LoginViewState(isLoading: false, isGoToMenu: result, hasError: false)
        }
    }
}
