//
//  CreateAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.02.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct CreateAddressViewState {
    let street: String
    let streetError: LocalizedStringKey?
    let streetSuggestionList: [StreetItem]
    let isSuggestionLoading: Bool
    let house: String
    let houseError: LocalizedStringKey?
    let flat: String
    let entrance: String
    let floor: String
    let comment: String
    let isCreateLoading: Bool
}
