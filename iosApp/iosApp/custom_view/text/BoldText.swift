//
//  BoldText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI

struct BoldLocalizedStringKey: View {
    let text:LocalizedStringKey
    var body: some View {
        Text(text)
            .titleMedium(weight: .bold)
            .foregroundColor(AppColor.onSurface)
    }
}

struct BoldText: View {
    let text:String
    var body: some View {
        Text(text)
            .titleMedium(weight: .bold)
            .foregroundColor(AppColor.onSurface)
    }
}

struct BoldText_Previews: PreviewProvider {
    static var previews: some View {
        BoldText(text: "Some text")
    }
}
