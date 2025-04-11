//
//  SearchEditText.swift
//  iosApp
//
//  Created by Марк Шавловский on 24.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import Combine
import SwiftUI

struct SearchEditTextView: View {
    let hint: String
    @Binding var text: String
    let limit: Int

    var list: [StreetItem]

    @State var prevSimbol = ""

    @Binding var errorMessage: LocalizedStringKey?

    @Binding var isLoading: Bool

    var textChanged: (String) -> Void

    var selectSuggetion: (StreetItem) -> Void

    var focusChangeListener: (Bool) -> Void

    var body: some View {
        VStack(spacing: 0) {
            ZStack(alignment: .trailing) {
                EditTextView(
                    hint: hint,
                    text: $text,
                    limit: limit,
                    errorMessage: $errorMessage,
                    textChanged: textChanged,
                    focusChangeListener: focusChangeListener
                )

                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppColor.primary))
                        .scaleEffect(0.6)
                        .padding(.trailing, 2)
                }
            }

            ForEach(list) { street in
                Button {
                    text = street.name
                    dropFocusFromTextField()
                    selectSuggetion(street)
                } label: {
                    HStack(spacing: 0) {
                        Text(
                            "\(Text(street.name).foregroundColor(AppColor.onSurface))\(Text(street.postfix ?? "").foregroundColor(AppColor.onSurfaceVariant))"
                        ).multilineTextAlignment(.leading)
                    }
                    .padding(Diems.SMALL_PADDING)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(AppColor.surface)
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .padding(.top, Diems.SMALL_PADDING)
                }
            }
        }
    }

    func dropFocusFromTextField() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
