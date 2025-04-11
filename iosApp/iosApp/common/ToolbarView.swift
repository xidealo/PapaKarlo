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

    var back: (() -> Void)? = nil
    @State private var showingAlert = false

    var body: some View {
        HStack(spacing: 0) {
            if let backAction = back {
                Button(action: {
                    backAction()
                }) {
                    Image(systemName: "arrow.backward")
                        .foregroundColor(AppColor.onSurface)
                }.padding(.vertical, Diems.SMALL_PADDING)
                    .padding(.leading, 8)
            }
            Text(title)
                .foregroundColor(AppColor.onSurface)
                .titleMedium(weight: .bold)
                .lineLimit(1)
                .padding(.vertical, Diems.MEDIUM_PADDING)
                .padding(.leading, 8)
            Spacer()
        }.background(AppColor.surface)
    }
}
