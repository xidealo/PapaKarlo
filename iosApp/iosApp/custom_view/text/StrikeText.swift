//
//  StrikeText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 12.03.2022.
//

import SwiftUI

struct StrikeText: View {
    let text:String
    var body: some View {
        Text(text)
            .strikethrough()
            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .thin, design: .default))
            .foregroundColor(Color("onSurface"))
    }
}


struct StrikeText_Previews: PreviewProvider {
    static var previews: some View {
        StrikeText(text:"220 R")
    }
}
