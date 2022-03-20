//
//  CafeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

struct CafeItemView: View {
    
    
    var cafeItem: CafeItem

    var body: some View {
        
        VStack{
            LargeHeaderText(text: cafeItem.address)
            
            HStack{
                Text(cafeItem.workingHours)
                Text(cafeItem.isOpenMessage).foregroundColor(cafeItem.isOpenColor)
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.bottom, Diems.MEDIUM_PADDING)
            .frame(maxWidth: .infinity, alignment: .leading)
            
        }.background(Color("surface")).cornerRadius(15)
       
    }
}

struct CafeView_Previews: PreviewProvider {
    static var previews: some View {
        CafeItemView(
            cafeItem: CafeItem(id: UUID(), address: "Kimry chapaevo 22a", workingHours: "9:00 - 22:00", isOpenMessage: "Open", isOpenColor: Color.green
            )
        )
    }
}
