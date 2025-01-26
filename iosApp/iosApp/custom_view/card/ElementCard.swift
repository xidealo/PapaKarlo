//
//  ElementText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct ElementCard: View {
    let text:String
    
    var body: some View {
        Text(text)
            .bodyLarge()
            .multilineTextAlignment(.leading)
            .padding(.vertical, 12)
            .padding(.horizontal, 16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .foregroundColor(AppColor.onSurface)
    }
}

struct ElementCardWithLocolized: View {
    let text: LocalizedStringKey
    
    var body: some View {
        Text(text)
            .bodyLarge()
            .multilineTextAlignment(.leading)
            .padding(.vertical, 12)
            .padding(.horizontal, 16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .foregroundColor(AppColor.onSurface)
    }
}

struct ElementText_Previews: PreviewProvider {
    static var previews: some View {
        ElementCard(text: "Text")
    }
}
