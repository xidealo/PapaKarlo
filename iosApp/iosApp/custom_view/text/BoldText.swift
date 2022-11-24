//
//  BoldText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 27.03.2022.
//

import SwiftUI

struct BoldText: View {
    let text:String
    var body: some View {
        Text(text)
            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
            .foregroundColor(Color("onSurface"))
    }
}

struct BoldText_Previews: PreviewProvider {
    static var previews: some View {
        BoldText(text: "Some text")
    }
}
