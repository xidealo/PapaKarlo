//
//  EditTextView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 11.04.2022.
//

import Combine
import SwiftUI

struct EditTextView: View {
    let hint: String
    @Binding var text: String
    let limit: Int
    var keyBoadrType = UIKeyboardType.default

    @Binding var errorMessage: LocalizedStringKey?

    var textChanged: (String) -> Void

    @State var isSelectedSSS: Bool = false

    var focusChangeListener: (Bool) -> Void = { _ in }

    var body: some View {
        VStack(spacing: 0) {
            TextField(
                hint,
                text: $text,
                onEditingChanged: { edit in
                    self.isSelectedSSS = edit
                    focusChangeListener(edit)
                }
            )
            .background(AppColor.surface)
            .font(Font.system(size: 16, weight: .regular, design: .default))
            .foregroundColor(AppColor.onSurface)
            .padding(16)
            .lineLimit(5)
            .cornerRadius(4)
            .overlay(
                RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                    .stroke(getRoundedColor(), lineWidth: 2)
            ).onReceive(Just(text)) { str in limitText(limit)
                textChanged(str)
            }
            .keyboardType(keyBoadrType)

            if let notNullErrorMessage = errorMessage {
                Text(notNullErrorMessage)
                    .bodySmall()
                    .foregroundColor(AppColor.error)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 4)
            }
        }
    }

    func getRoundedColor() -> Color {
        if errorMessage != nil {
            return AppColor.error
        }

        if isSelectedSSS {
            return AppColor.primary
        } else {
            return AppColor.onSurfaceVariant
        }
    }

    func limitText(_ upper: Int) {
        if text.count > upper {
            text = String(text.prefix(upper))
        }
    }
}
