//
//  TextCardWithDivider.swift
//  iosApp
//
//  Created by Марк Шавловский on 14.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TextCardWithDivider: View {

    let placeHolder: String
    let text: String

    var body: some View {
        VStack(spacing: 0) {
            Text(placeHolder)
                .labelSmall(weight: .medium)
                .foregroundColor(AppColor.onSurfaceVariant)
                .frame(maxWidth: .infinity, alignment: .leading)

            Text(text)
                .bodyMedium()
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(AppColor.onSurface)
                .multilineTextAlignment(.leading)

            FoodDeliveryDivider()
                .padding(.top, 8)
        }.padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.vertical, Diems.SMALL_PADDING)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
