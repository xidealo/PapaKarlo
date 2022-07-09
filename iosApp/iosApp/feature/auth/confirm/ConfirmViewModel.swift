//
//  ConfirmViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared


class ConfirmViewModel:ObservableObject {
    
    
    @Published var confirmViewState:ConfirmViewState = ConfirmViewState(isLoading: false, isGoToProfile: false)
    let auth = AuthManager()
    
    func checkCode(code:String){
        confirmViewState  = ConfirmViewState(isLoading: true, isGoToProfile: false)

        auth.verifyCode(smsCode: code) { result in
            self.confirmViewState  = ConfirmViewState(isLoading: false, isGoToProfile: result)
        }
    }
    
}
