//
//  LoginViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 07.07.2022.
//

import Foundation

class LoginViewState :NSObject {
    
    var isLoading:Bool
    var isGoToMenu:Bool
    var hasError:Bool
    
    init(isLoading:Bool, isGoToMenu:Bool, hasError:Bool){
        self.isLoading = isLoading
        self.isGoToMenu = isGoToMenu
        self.hasError = hasError
    }
    
}
