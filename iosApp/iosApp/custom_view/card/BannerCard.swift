//
//  BannerCard.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct BannerCard: View {
    let title: String
    let text: String
    let icon: String
    let cardColor: Color

    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    IconImage(imageName: icon)
                        .foregroundColor(AppColor.onStatus)

                    Text(title)
                        .titleMedium(weight: .medium)
                        .padding(.leading, 8)
                        .foregroundColor(AppColor.onStatus)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }

                Text(text)
                    .bodyLarge()
                    .padding(.top, 8)
                    .foregroundColor(AppColor.onStatus)
                    .frame(maxWidth: .infinity, alignment: .leading)

            }.padding(.horizontal, 16)
                .padding(.vertical, 16)
        }
        .frame(maxWidth: .infinity)
        .background(cardColor)
        .cornerRadius(16)
    }
}
