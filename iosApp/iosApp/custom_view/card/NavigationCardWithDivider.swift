//
//  NavigationCardWithDivider.swift
//  iosApp
//
//  Created by Марк Шавловский on 22.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct NavigationCardWithDivider: View {
    let icon: String?

    let label: String
    let value: String?
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    VStack(spacing: 0) {
                        if value == nil {
                            Text(label)
                                .bodyLarge()
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .foregroundColor(AppColor.onSurface)
                        } else {
                            Text(label)
                                .labelSmall()
                                .foregroundColor(AppColor.onSurfaceVariant)
                                .frame(maxWidth: .infinity, alignment: .leading)
                        }

                        if let value = value {
                            Text(value)
                                .bodyMedium()
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .foregroundColor(AppColor.onSurface)
                                .multilineTextAlignment(.leading)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    Image(systemName: "chevron.right")
                        .foregroundColor(AppColor.onSurfaceVariant)
                }
                .padding(.vertical, 12)
                FoodDeliveryDivider()
            }
            .background(AppColor.surface)
        }
    }
}
