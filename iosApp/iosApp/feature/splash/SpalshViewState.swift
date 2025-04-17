//
//  SpalshViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class SplashViewState: NSObject {
    let splashState: SplashState

    init(splashState: SplashState) {
        self.splashState = splashState
    }
}

enum SplashState {
    case checking
    case isGoSelectCity
    case isGoMenu
}
