//
//  Motivation.swift
//  iosApp
//
//  Created by Марк Шавловский on 24.06.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct Motivation: View {
    
    
    let motivation: MotivationUi
    
    var body: some View {
        HStack(spacing:0){
            VStack(spacing:0){
                HStack(spacing:0){
                    getMotivationIcon(motivation: motivation)
                    getMotivationText(motivation: motivation)
                        .foregroundColor(AppColor.onSurface)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.leading, 8)
                    
                }
                
                if let progress = getMotivationProgress(motivation: motivation){
                    ProgressView(value: progress)
                        .tint(progress < 1 ? AppColor.warning : AppColor.positive)
                        .frame(height: 8)
                        .progressViewStyle(LinearProgressViewStyle())
                        .cornerRadius(4)
                        .animation(.easeIn(duration: 3), value: progress)
                        .padding(.top, 4)
                }
            }.padding(.horizontal, 16)
                .padding(.top, 16)
        }
    }
    
    
    func getMotivationProgress(motivation: MotivationUi) -> Float?  {
        switch(motivation){
        case .MinOrderCost(_):
            return nil
            
        case .ForLowerDelivery(_, let progress, _):
            return progress
            
        case .LowerDeliveryAchieved(_):
            return 1
        }
    }
    
    func getMotivationText(motivation: MotivationUi) -> some View  {
        switch(motivation){
        case .MinOrderCost(let cost):
            return (
                Text("Минимальая сумма заказа: ").bodySmall() + Text(cost).bodySmall(weight: .bold)
            )
            
        case .ForLowerDelivery(let increaseAmountBy, _, let isFree):
            return isFree ? (Text("Еще ") + Text(increaseAmountBy).bold() + Text(" до бесплатной доставки")).bodySmall() : (Text("Еще ") + Text(increaseAmountBy).bold() + Text(" до уменьшения стоимости доставки")).bodySmall()
        case .LowerDeliveryAchieved(let isFree):
            return Text(isFree ? "Бесплатная доставка" : "Уменьшенная стоимость доставки").bodySmall()
        }
    }
    
    func getMotivationIcon(motivation: MotivationUi) -> some View  {
        switch(motivation){
        case .MinOrderCost(_):
            return IconImage(imageName: "ic_warning")
                .foregroundColor(AppColor.warning)
            
        case .ForLowerDelivery(_, _, _):
            return IconImage(imageName: "ic_delivery")
                .foregroundColor(AppColor.onSurfaceVariant)
            
        case .LowerDeliveryAchieved(_):
            return IconImage(imageName: "ic_delivery")
                .foregroundColor(AppColor.onSurfaceVariant)
        }
    }
}
