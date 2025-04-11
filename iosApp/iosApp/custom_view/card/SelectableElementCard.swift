//
//  SelectableElementCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.04.2022.
//

import SwiftUI

struct SelectableElementCard: View {
    var text: String? = nil
    var locolized: LocalizedStringKey? = nil

    let isSelected: Bool
    let isEnabled: Bool

    var body: some View {
        HStack(spacing: 0) {
            if text == nil {
                Text(locolized ?? LocalizedStringKey(""))
                    .foregroundColor(isEnabled ? AppColor.onSurface : AppColor.onSurfaceVariant)
                    .multilineTextAlignment(.leading)
            } else {
                Text(text ?? "")
                    .foregroundColor(isEnabled ? AppColor.onSurface : AppColor.onSurfaceVariant)
                    .multilineTextAlignment(.leading)
            }

            Spacer()
            if isSelected {
                IconImage(
                    width: 16,
                    height: 16,
                    imageName: "CheckIcon"
                )
                .foregroundColor(AppColor.onSurfaceVariant)
            }
        }
        .padding(.vertical, 12)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}
