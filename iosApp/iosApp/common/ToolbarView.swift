//
//  ToolbarView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.04.2022.
//

import shared
import SwiftUI

struct ToolbarView: View {
    let title: LocalizedStringKey

    var back: (() -> Void)?
    @State private var showingAlert = false
    var foodDeliveryAction: FoodDeliveryAction?

    var body: some View {
        HStack(spacing: 0) {
            if let backAction = back {
                Button(action: {
                    backAction()
                }) {
                    Image(systemName: "arrow.backward")
                        .foregroundColor(AppColor.onSurface)
                }.padding(.vertical, Diems.SMALL_PADDING)
                    .padding(.leading, 16)
            }
            Text(title)
                .foregroundColor(AppColor.onSurface)
                .titleMedium(weight: .bold)
                .lineLimit(1)
                .padding(.vertical, Diems.MEDIUM_PADDING)
                .padding(.leading, back == nil ? 16 : 8)
            Spacer()

            if let foodDeliveryAction = foodDeliveryAction {
                Button(action: {
                    foodDeliveryAction.onClick()
                }) {
                    IconImage(width: 24, height: 24, imageName: foodDeliveryAction.icon)
                        .foregroundColor(AppColor.onSurfaceVariant)
                }.padding(.vertical, Diems.SMALL_PADDING)
                    .padding(.trailing, 16)
            }

        }.background(AppColor.surface)
    }
}

struct FoodDeliveryAction {
    let icon: String
    let onClick: () -> Void

    init(iconSystemName: String, onClick: @escaping () -> Void) {
        self.icon = iconSystemName
        self.onClick = onClick
    }
}
