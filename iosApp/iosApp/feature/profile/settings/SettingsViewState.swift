//
//  SettingsViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 14.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation

struct SettingsViewState {
    let phoneNumber: String
    let selectedCityName: String
    let state: State

    enum State {
        case success
        case loading
        case error
    }
}
