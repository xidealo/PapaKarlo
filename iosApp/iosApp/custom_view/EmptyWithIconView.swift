//
//  EmptyWithIconView.swift
//  iosApp
//
//  Created by Марк Шавловский on 06.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct EmptyWithIconView: View {
    let imageName: String
    let title: LocalizedStringKey
    let secondText: LocalizedStringKey

    var body: some View {
        VStack(spacing: 0) {
            ZStack(alignment: .center) {
                Circle()
                    .fill(
                        AppColor.primary
                            .opacity(0.8)
                    )
                    .frame(width: 120, height: 120)
                IconImage(
                    width: 64,
                    height: 64,
                    imageName: imageName
                )
                .foregroundColor(AppColor.onStatus)
            }

            BoldLocalizedStringKey(text: title)
                .padding(.top, 32)

            Text(secondText)
                .bodyLarge()
                .multilineTextAlignment(.center)
                .padding(.top, Diems.SMALL_PADDING)
        }
    }
}
