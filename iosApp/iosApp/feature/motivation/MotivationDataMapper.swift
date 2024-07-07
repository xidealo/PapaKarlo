//
//  MotivationDataMapper.swift
//  iosApp
//
//  Created by Марк Шавловский on 22.06.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension MotivationData{
    func getMotivationUi() -> MotivationUi? {
            switch self {
            case let minOrderCost as MotivationDataMinOrderCost:
                return MotivationUi.MinOrderCost(minOrderCost.cost)
            case let forLowerDelivery as MotivationDataForLowerDelivery:
                return MotivationUi.ForLowerDelivery(
                    forLowerDelivery.increaseAmountBy,
                    forLowerDelivery.progress,
                    forLowerDelivery.isFree
                )
            case let lowerDeliveryAchieved as MotivationDataLowerDeliveryAchieved:
                return MotivationUi.LowerDeliveryAchieved(lowerDeliveryAchieved.isFree)
            default:
                return nil
            }
    }
}
