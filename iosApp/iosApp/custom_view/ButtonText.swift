//
//  ButtonText.swift
//  iosApp
//
//  Created by Марк Шавловский on 26.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ButtonText: View {
    let text:String
    var background = AppColor.primary
    var foregroundColor = AppColor.surface

    var body: some View {
        Text(text)
            .labelLarge(weight: .medium)
            .frame(maxWidth: .infinity, maxHeight: 40)
            .foregroundColor(foregroundColor)
            .background(background)
            .cornerRadius(Diems.BUTTON_RADIUS)
            .multilineTextAlignment(.center)
    }
}
