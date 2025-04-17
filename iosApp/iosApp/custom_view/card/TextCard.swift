//
//  TextCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct TextCard: View {
    let placeHolder: String
    let text: String

    var body: some View {
        VStack(spacing: 0) {
            Text(placeHolder)
                .labelSmall(weight: .medium)
                .foregroundColor(AppColor.onSurfaceVariant)
                .frame(maxWidth: .infinity, alignment: .leading)

            Text(text)
                .bodyMedium()
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(AppColor.onSurface)
                .multilineTextAlignment(.leading)
        }.padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.vertical, Diems.SMALL_PADDING)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct TextCard_Previews: PreviewProvider {
    static var previews: some View {
        TextCard(placeHolder: "placeHolder", text: "text")
    }
}
