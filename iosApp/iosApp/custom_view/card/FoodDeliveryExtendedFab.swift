//
//  FoodDeliveryExtendedFab.swift
//  iosApp
//
//  Created by Марк Шавловский on 15.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct FoodDeliveryExtendedFab: View {
    let text: String
    let onClick: () -> Void
    var icon: String? = nil
    var iconBadge: String? = nil
    var maxWidth: CGFloat? = nil
    
    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 8) {
                if let icon = icon {
                    ZStack(alignment: .topTrailing) {
                        IconImage(imageName: icon)
                            .foregroundColor(AppColor.onPrimary)
                        
                        if let badgeText = iconBadge {
                            Text(badgeText)
                                .labelSmall(weight: .medium)
                                .foregroundColor(AppColor.primary)
                                .padding(4)
                                .background(AppColor.onPrimary)
                                .clipShape(Circle())
                                .offset(x: 8, y: -8)
                        }
                    }
                }
                
                Text(text)
                    .labelLarge()
                    .foregroundColor(AppColor.onPrimary)
                    .padding(.leading, iconBadge == nil ? 0 : 8)
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .frame(maxWidth: maxWidth)
        }
        .background(AppColor.primary)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .shadow(radius: 6)
    }
}
