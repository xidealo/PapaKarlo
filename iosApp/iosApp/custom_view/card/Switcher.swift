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
    let action: (_ isDelivery:Bool) -> Void
    
    var body: some View {
        HStack{
            if isLeftSelected{
                Button(action: {
                    print("button pressed")
                    isLeftSelected = true
                    action(true)
                }) {
                    SelectedSwicher(title: leftTitle)
                }
                
                Button(action: {
                    print("button pressed")
                    isLeftSelected = false
                    action(false)
                }) {
                    Text(rightTitle)
                        .labelLarge(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth:.infinity)
                }
            }else{
                Button(action: {
                    print("button pressed")
                    isLeftSelected = true
                    action(true)
                }) {
                    Text(leftTitle)
                        .labelLarge(weight: .medium)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .frame(maxWidth:.infinity)
                }
                
                Button(action: {
                    print("button pressed")
                    isLeftSelected = false
                    action(false)
                }) {
                    SelectedSwicher(title: rightTitle).frame(maxWidth:.infinity)
                }
            }
        }
        .padding(4)
        .background(AppColor.surface)
        .cornerRadius(Diems.BUTTON_RADIUS)
        .frame(maxWidth:.infinity)
    }
}

struct Switcher_Previews: PreviewProvider {
    static var previews: some View {
        Switcher(leftTitle: "Доставка", rightTitle: "Самовывоз", isLeftSelected: .constant(true), action: {_ in })
    }
}

struct SelectedSwicher: View {
    let title:String

    var body: some View {
        Text(title)
            .labelLarge(weight: .medium)
            .frame(maxWidth:.infinity)
            .padding(Diems.SMALL_PADDING)
            .background(AppColor.primary)
            .foregroundColor(AppColor.onPrimary)
            .cornerRadius(Diems.BUTTON_RADIUS)
    }
}
