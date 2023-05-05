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
    let action: () -> Void
    let isShowRightArrow:Bool
    
    init(icon:String?, label:String, isSystemImageName:Bool, isShowRightArrow:Bool, action: @escaping () -> Void) {
        self.icon = icon
        self.label = label
        self.action = action
        self.isSystemImageName = isSystemImageName
        self.isShowRightArrow = isShowRightArrow
    }
    
    var body: some View {
        Button(action: action){
           CardView(
            icon: icon,
            label: label,
            isSystemImageName: isSystemImageName,
            isShowRightArrow: isShowRightArrow
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
