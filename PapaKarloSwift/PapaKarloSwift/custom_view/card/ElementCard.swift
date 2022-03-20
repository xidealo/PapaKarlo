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
        Text(text).padding().frame(maxWidth: .infinity, alignment: .leading).background(Color("surface")).cornerRadius(Diems.MEDIUM_RADIUS)
        
    }
}

struct ElementText_Previews: PreviewProvider {
    static var previews: some View {
        ElementCard(text: "Text")
    }
}
