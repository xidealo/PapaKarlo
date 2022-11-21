//
//  LoginViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 07.07.2022.
//

import Foundation

class LoginViewState :NSObject, NSCopying {
    
    var isLoading:Bool
    var actionList:[LoginAction]

    init(isLoading:Bool, actionList:[LoginAction]){
        self.isLoading = isLoading
        self.actionList = actionList
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = LoginViewState(
            isLoading : isLoading,
            actionList : actionList
        )
        return copy
    }
}
