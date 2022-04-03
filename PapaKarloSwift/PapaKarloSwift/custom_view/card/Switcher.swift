//
//  Switcher.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.04.2022.
//

import SwiftUI

struct Switcher: View {
    
    let leftTitle:String
    let rightTitle:String
    
    var body: some View {
        HStack{
            Text(leftTitle)
            Text(rightTitle)
        }
    }
}

struct Switcher_Previews: PreviewProvider {
    static var previews: some View {
        Switcher(leftTitle: "Доставка", rightTitle: "Самовывоз")
    }
}
