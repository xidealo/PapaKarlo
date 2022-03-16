//
//  PlaceholderText.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct PlaceholderText: View {
    let text:String
    
    var body: some View {
        Text(text).frame(maxWidth:.infinity, alignment: .leading).font(.system(size: Diems.SMALL_TEXT_SIZE, weight: .thin, design: .default))
    }
}

struct PlaceholderText_Previews: PreviewProvider {
    static var previews: some View {
        PlaceholderText(text: "placeholder")
    }
}
