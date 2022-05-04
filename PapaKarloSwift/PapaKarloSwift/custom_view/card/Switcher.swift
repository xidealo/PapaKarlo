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
    @Binding var isLeftSelected:Bool
    
    var body: some View {
        HStack{
            if isLeftSelected{
                Button(action: {
                    print("button pressed")
                    isLeftSelected = true
                }) {
                    SelectedSwicher(title: leftTitle)
                }
                
                Button(action: {
                    print("button pressed")
                    isLeftSelected = false
                }) {
                    Text(rightTitle).foregroundColor(Color("onSurfaceVariant")).frame(maxWidth:.infinity)
                }
            }else{
                Button(action: {
                    print("button pressed")
                    isLeftSelected = true
                }) {
                    Text(leftTitle).foregroundColor(Color("onSurfaceVariant")).frame(maxWidth:.infinity)
                }
                
                Button(action: {
                    print("button pressed")
                    isLeftSelected = false
                }) {
                    SelectedSwicher(title: rightTitle).frame(maxWidth:.infinity)
                }
            }
        }.padding(3).background(Color("surface")).cornerRadius(Diems.MEDIUM_RADIUS).frame(maxWidth:.infinity)
    }
}

struct Switcher_Previews: PreviewProvider {
    static var previews: some View {
        Switcher(leftTitle: "Доставка", rightTitle: "Самовывоз", isLeftSelected: .constant(true))
    }
}

struct SelectedSwicher: View {
    let title:String

    var body: some View {
        Text(title)
            .frame(maxWidth:.infinity).padding(Diems.SMALL_PADDING)
            .background(Color("primary"))
            .foregroundColor(Color("onPrimary"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}