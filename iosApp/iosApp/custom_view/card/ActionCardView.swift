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
            HStack{
                if icon != nil{
                    if(isSystemImageName){
                        Image(systemName: icon ?? "").foregroundColor(Color("iconColor"))
                    }else{
                        Image(icon ?? "").resizable().frame(width: 23, height: 24).foregroundColor(Color("iconColor"))
                    }
                }
                Text(label).frame(maxWidth:.infinity, alignment: .leading).foregroundColor(Color("onSurface"))
                if isShowRightArrow{
                    Image(systemName:"chevron.right").foregroundColor(Color("onSurfaceVariant"))
                }
            }.frame(maxWidth:.infinity)
            .padding(Diems.MEDIUM_PADDING)
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}

struct ActionCardView_Previews: PreviewProvider {
    static var previews: some View {
        ActionCardView(icon: "DeveloperIcon", label: "Label",
                       isSystemImageName: false, isShowRightArrow: true, action: {})
    }
}
