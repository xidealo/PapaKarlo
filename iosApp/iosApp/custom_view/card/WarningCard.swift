//
//  WarningCard.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct WarningCard: View {
    let title: String
    let icon: String
    let cardColor: Color

    var body: some View {
        ZStack {
            HStack(spacing: 0) {
                IconImage(imageName: icon)
                    .foregroundColor(AppColor.onStatus)

                Text(title)
                    .bodyLarge()
                    .padding(.leading, 8)
                    .foregroundColor(AppColor.onStatus)
                    .frame(maxWidth: .infinity, alignment: .leading)

            }.padding(.horizontal, 16)
                .padding(.vertical, 12)
        }
        .frame(maxWidth: .infinity)
        .background(cardColor)
        .cornerRadius(16)
    }
}
