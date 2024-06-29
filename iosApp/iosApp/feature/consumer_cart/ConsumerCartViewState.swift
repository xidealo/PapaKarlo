//
//  ConsumerCartViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 17.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

struct ConsumerCartViewState {
    var state: ConsumerCartState
}

enum ConsumerCartState{
    case loading
    case error
    case success([CartProductItemUi], [MenuProductItem], BottomPanelInfoUi?)
}

struct CartProductItemUi: Identifiable {
    var id: String
    var name: String
    var newCost: String
    var oldCost: String?
    var photoLink: String
    var count: Int
    var additions: String?
    var isLast: Bool
}

struct BottomPanelInfoUi {
    var motivation: MotivationUi?
    var discount: String?
    var oldTotalCost: String?
    var newTotalCost: String
}

enum MotivationUi {
    case MinOrderCost(String)
    case ForLowerDelivery(String, Float, Bool)
    case LowerDeliveryAchieved(Bool)
}
