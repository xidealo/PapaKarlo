//
//  ConfirmViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ConfirmViewModel: ObservableObject {
    
    @Published var confirmViewState:ConfirmViewState = ConfirmViewState(
        confirmState: ConfirmState.success, actionList: []
    )
    
    func checkCode(code:String){
       
    }
    
    func clearActions(){
        self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.success, actionList: [])
    }
    
    func resendCode(phone:String){
        //combine with func on Login
    }
}

enum ConfirmState{
    case success, loading, error
}

enum ConfirmAction {
   case back, showLoginError, showCodeError
}
