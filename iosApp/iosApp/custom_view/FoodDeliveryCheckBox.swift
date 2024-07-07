//
//  CheckBox.swift
//  iosApp
//
//  Created by Марк Шавловский on 05.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct FoodDeliveryCheckBox: View {
    
    let isSelected: Bool
    
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            ZStack{
                IconImage(
                    width: 24,
                    height: 24,
                    imageName: isSelected ? "ic_enabled_checkbox" : "ic_disabled_checkbox"
                )
                .foregroundColor(
                    isSelected ? AppColor.primary : AppColor.onSurfaceVariant
                )
                
                if(isSelected) {
                    IconImage(width: 12, height: 10, imageName: "CheckIcon")
                        .foregroundColor(
                            AppColor.onPrimary
                        )
                }
            }
        }
    }
}
