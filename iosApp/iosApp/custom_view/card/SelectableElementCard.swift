//
//  SelectableElementCard.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.04.2022.
//

import SwiftUI

struct SelectableElementCard: View {
    var text:String? = nil
    var locolized:LocalizedStringKey? = nil
    
    let isSelected:Bool
    
    var body: some View {
        HStack(spacing: 0){
            
            if(text == nil){
                Text(locolized ?? LocalizedStringKey(""))
                    .foregroundColor(AppColor.onSurface)
                    .multilineTextAlignment(.leading)
            }else{
                Text(text ?? "")
                    .foregroundColor(AppColor.onSurface)
                    .multilineTextAlignment(.leading)
            }
   
            Spacer()
            if(isSelected){
                IconImage(
                    width: 16,
                    height: 16,
                    imageName: "CheckIcon"
                )
                .foregroundColor(AppColor.onSurfaceVariant)
            }
        }
        .padding(.vertical, 12)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(AppColor.surface)
        .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct SelectableElementCard_Previews: PreviewProvider {
    static var previews: some View {
        SelectableElementCard(text: "Text", isSelected: true)
    }
}
