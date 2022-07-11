//
//  LoginViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 07.07.2022.
//

import Foundation


class LoginViewState :NSObject {
    
    let isLoading:Bool
    let isGoToMenu:Bool
    
    init(isLoading:Bool, isGoToMenu:Bool){
        self.isLoading = isLoading
        self.isGoToMenu = isGoToMenu
    }
    
}
