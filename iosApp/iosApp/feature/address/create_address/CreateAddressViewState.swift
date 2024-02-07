//
//  CreateAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.02.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct CreateAddressViewState {
    let street: String
    let streetErrorStringId: Int?
    let streetSuggestionList: [StreetItem]
    let isSuggestionLoading: Bool
    let house: String
    let houseErrorStringId: Int?
    let flat: String
    let entrance: String
    let floor: String
    let comment: String
    let isCreateLoading: Bool
}
