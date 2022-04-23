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
        HStack{
            Text(text)
                .foregroundColor(Color("onSurface"))
            Spacer()
            if(isSelected){
                Image(systemName: "checkmark").foregroundColor(Color("iconColor"))
            }
        }.padding().frame(maxWidth: .infinity, alignment: .leading)
        .background(Color("surface"))
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct SelectableElementCard_Previews: PreviewProvider {
    static var previews: some View {
        SelectableElementCard(text: "Text", isSelected: true)
    }
}
