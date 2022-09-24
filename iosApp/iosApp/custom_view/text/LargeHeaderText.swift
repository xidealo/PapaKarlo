//
//  LargeHeaderText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct LargeHeaderText: View {
    let text:String
    
    var body: some View {
        Text(text)
            .foregroundColor(Color("onSurface"))
            .frame(maxWidth: .infinity, alignment: .leading)
            .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
    }
}

struct LargeHeaderText_Previews: PreviewProvider {
    static var previews: some View {
        LargeHeaderText(text: "Header")
    }
}
