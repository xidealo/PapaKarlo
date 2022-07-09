//
//  ConfirmViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class ConfirmViewState :NSObject {
    
    let isLoading:Bool
    let isGoToProfile:Bool
    
    init(isLoading:Bool, isGoToProfile:Bool){
        self.isLoading = isLoading
        self.isGoToProfile = isGoToProfile
    }
    
}
