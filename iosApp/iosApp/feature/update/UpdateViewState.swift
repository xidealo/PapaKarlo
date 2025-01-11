//
//  UpdateViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

struct UpdateViewState {
    let state: UpdateState
}

enum UpdateState {
    case loading
    case success(Link?)
    case error
}
