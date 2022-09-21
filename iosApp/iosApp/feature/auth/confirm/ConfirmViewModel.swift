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
        confirmState: ConfirmState.success
    )
    
    let auth : AuthManager
    private var isGoToProfile:Bool

    init(auth:AuthManager, isGoToProfile:Bool){
        self.auth = auth
        self.isGoToProfile = isGoToProfile
    }
    
    func checkCode(code:String){
        confirmViewState  = ConfirmViewState(confirmState: ConfirmState.loading)
        
        auth.verifyCode(smsCode: code) { result in
            if(result){
                iosComponent.provideIUserInteractor().login(firebaseUserUuid: self.auth.getCurrentUserUuid(), firebaseUserPhone: self.auth.getCurrentUserPhone()) { err  in
                    if(err == nil){
                        DispatchQueue.main.async{
                            if(self.isGoToProfile){
                                self.confirmViewState = ConfirmViewState(confirmState: ConfirmState.goToProfile)
                            }else{
                                self.confirmViewState = ConfirmViewState(confirmState: ConfirmState.goToCreateOrder)
                            }
                        }
                    }else{
                        self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.error)
                    }
                }
            }else{
                self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.error)
            }
        }
    }
    
    func dporState(){
        self.confirmViewState  = ConfirmViewState(confirmState: ConfirmState.success)
    }
    
}

enum ConfirmState{
    case success, loading, error, goToProfile, goToCreateOrder
}
