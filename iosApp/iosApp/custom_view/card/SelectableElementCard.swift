//
//  SelectableElementCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.04.2022.
//

import SwiftUI

struct SelectableElementCard: View {
    let text:String
    let isSelected:Bool

    var body: some View {
        HStack(spacing: 0){
            Text(text)
                .foregroundColor(AppColor.onSurface)
            Spacer()
            if(isSelected){
                Image(systemName: "checkmark")
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

struct SelectableElementCard_Previews: PreviewProvider {
    static var previews: some View {
        SelectableElementCard(text: "Text", isSelected: true)
    }
}
