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
        isLoading: false, actionList: []
    )
    
    let auth : AuthManager
    
    init(auth:AuthManager){
        self.auth = auth
    }
    
    func sendCodeToPhone(phone:String){
        
        if(phone.count < 17){
                        
            (self.loginViewState.copy() as! LoginViewState).apply { copiedState in
                copiedState.isLoading = false
                copiedState.actionList.append(LoginAction.hasError)
                self.loginViewState = copiedState
            }
            return
        }
        
        let formattedPhone = phone.replace(string: "(", replacement: "")
            .replace(string: ")", replacement: "")
            .replace(string: "-", replacement: "")
            .replace(string: " ", replacement: "")
        
        (self.loginViewState.copy() as! LoginViewState).apply { copiedState in
            copiedState.isLoading = true
            self.loginViewState = copiedState
        }
        
        auth.startAuth(phoneNumber: formattedPhone) { result in            
            (self.loginViewState.copy() as! LoginViewState).apply { copiedState in
                copiedState.isLoading = false
                copiedState.actionList.append(LoginAction.goToConfirm)
                self.loginViewState = copiedState
            }
        }
    }
    
    func clearActions(){
        (self.loginViewState.copy() as! LoginViewState).apply { copiedState in
            copiedState.actionList = []
            self.loginViewState = copiedState
        }
    }
}

enum LoginAction {
    case goToConfirm, hasError
}
