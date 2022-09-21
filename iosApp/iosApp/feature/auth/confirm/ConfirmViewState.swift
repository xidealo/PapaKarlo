//
//  ConfirmViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class ConfirmViewState :NSObject {
    
    var confirmState:ConfirmState
    
    init(confirmState:ConfirmState){
        self.confirmState = confirmState
    }
    
}
