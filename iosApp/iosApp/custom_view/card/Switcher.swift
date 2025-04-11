//
//  Switcher.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct Switcher: View {
    let leftTitle: String
    let rightTitle: String
    let isLeftSelected: Bool
    let action: (_ isDelivery: Bool) -> Void

    var body: some View {
        HStack(spacing: 0) {
            if isLeftSelected {
                Button(
                    action: {
                        action(true)
                    }
                ) {
                    SelectedSwicher(title: leftTitle)
                }

                Button(action: {
                    action(false)
                }) {
                    Text(rightTitle)
                        .labelLarge(weight: .medium)
                        .foregroundColor(AppColor.onSecondary)
                        .frame(maxWidth: .infinity)
                }
            } else {
                Button(action: {
                    action(true)
                }) {
                    Text(leftTitle)
                        .labelLarge(weight: .medium)
                        .foregroundColor(AppColor.onSecondary)
                        .frame(maxWidth: .infinity)
                }

                Button(action: {
                    action(false)
                }) {
                    SelectedSwicher(title: rightTitle).frame(maxWidth: .infinity)
                }
            }
        }
        .padding(4)
        .background(AppColor.stroke)
        .cornerRadius(Diems.BUTTON_RADIUS)
        .frame(maxWidth: .infinity)
    }
}

struct SelectedSwicher: View {
    let title: String

    var body: some View {
        Text(title)
            .labelLarge(weight: .medium)
            .frame(maxWidth: .infinity)
            .padding(Diems.SMALL_PADDING)
            .background(AppColor.surface)
            .foregroundColor(AppColor.primary)
            .cornerRadius(Diems.BUTTON_RADIUS)
    }
}
