//
//  CountPicker.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI

struct CountPicker: View {
    
    let count:String
    let plusAction: () -> Void
    let minusAction: () -> Void

    var body: some View {
        
        HStack{
            Group{
                Button(action: minusAction) {
                    Text("-").padding(.horizontal, Diems.SMALL_PADDING)
                }
                Text(count)
                Button(action:plusAction) {
                    Text("+").padding(.horizontal, Diems.SMALL_PADDING)
                }
            }.foregroundColor(Color("onPrimary"))
        }.padding(.horizontal, 12).padding(.vertical, Diems.SMALL_PADDING).background(Color("primary")).cornerRadius(Diems.MEDIUM_RADIUS)
    }
    
}

struct CountPicker_Previews: PreviewProvider {
    static var previews: some View {
        CountPicker(count: "4") {
            
        } minusAction: {
            
        }

    }
}
