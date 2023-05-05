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
        HStack(spacing:0){
            Group{
                Button(action: minusAction) {
                    Text("-")
                        .padding(.horizontal, 8)
                }
                Text(count)
                    .bodySmall()
                Button(action:plusAction) {
                    Text("+")
                        .padding(.horizontal, 8)
                }
            }
            .foregroundColor(AppColor.primary)
        }
        .padding(.horizontal, 8)
        .padding(.vertical, Diems.SMALL_PADDING)
        .overlay(
            RoundedRectangle(cornerRadius: Diems.BUTTON_RADIUS)
                .stroke(AppColor.primary, lineWidth: 2)
        )
        .cornerRadius(Diems.BUTTON_RADIUS)
    }
    
}

struct CountPicker_Previews: PreviewProvider {
    static var previews: some View {
        CountPicker(count: "4") {
            
        } minusAction: {
            
        }
        
    }
}
