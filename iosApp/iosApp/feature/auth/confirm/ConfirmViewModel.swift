//
//  ConfirmViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import FirebaseAuth

class ConfirmViewModel: ObservableObject {
    
    @Published var confirmViewState:ConfirmViewState = ConfirmViewState(
        confirmState: ConfirmState.success, actionList: []
    )
    
    let auth : AuthManager

    init(auth:AuthManager){
        self.auth = auth
    }
    
    func checkCode(code:String){
        confirmViewState  = ConfirmViewState(confirmState: ConfirmState.loading, actionList: [])
        
        auth.verifyCode(smsCode: code) { result in
            if(result){
                iosComponent.provideIUserInteractor().login(firebaseUserUuid: self.auth.getCurrentUserUuid(), firebaseUserPhone: self.auth.getCurrentUserPhone()) { err  in
                    if(err == nil){
                        DispatchQueue.main.async{
                            self.confirmViewState = ConfirmViewState(confirmState: ConfirmState.success, actionList: [ConfirmAction.back])
                        }
                    }else{
                        self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.error, actionList: [])
                    }
                }
            }else{
                self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.error, actionList: [])
            }
        }
    }
    
    func clearActions(){
        self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.success, actionList: [])
    }
}

enum ConfirmState{
    case success, loading, error
}

enum ConfirmAction {
   case back
}
