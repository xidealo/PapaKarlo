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
    var street: String
    var streetError: LocalizedStringKey?
    var streetSuggestionList: [StreetItem]
    var isSuggestionLoading: Bool
    var house: String
    var houseError: LocalizedStringKey?
    var flat: String
    var entrance: String
    var floor: String
    var comment: String
    var isCreateLoading: Bool
}
