//
//  ActionCardView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.04.2022.
//

import SwiftUI

struct ActionCardView: View {
    
    let icon:String?
    
    let label:String
    let isSystemImageName:Bool
    let isShowRightArrow:Bool
    let action: () -> Void
    var placeHolder:LocalizedStringKey? = nil

    var body: some View {
        Button(action: action){
           CardView(
            icon: icon,
            label: label,
            isSystemImageName: isSystemImageName,
            isShowRightArrow: isShowRightArrow,
            placeHolder: placeHolder
           )
        }
    }
}

struct ActionCardView_Previews: PreviewProvider {
    static var previews: some View {
        ActionCardView(icon: "DeveloperIcon", label: "Label",
                       isSystemImageName: false, isShowRightArrow: true, action: {})
    }
}
