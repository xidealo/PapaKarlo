//
//  CafeListViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 27.11.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct CafeListViewState {
    var state: CafeListCartState
}

enum CafeListCartState {
    case loading
    case error
    case success([CafeItem], CartCostAndCount)
}
