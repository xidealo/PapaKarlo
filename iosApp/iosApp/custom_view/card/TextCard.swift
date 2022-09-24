//
//  TextCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 15.03.2022.
//

import SwiftUI

struct TextCard: View {
    let placeHolder:String
    let text:String

    var body: some View {
        VStack{
            PlaceholderText(text:placeHolder).frame(maxWidth:.infinity, alignment: .leading)
            Text(text).frame(maxWidth:.infinity, alignment: .leading)
        }.padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.vertical, Diems.SMALL_PADDING)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct TextCard_Previews: PreviewProvider {
    static var previews: some View {
        TextCard(placeHolder: "placeHolder", text: "text")
    }
}
