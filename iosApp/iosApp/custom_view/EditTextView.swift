//
//  EditTextView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 11.04.2022.
//

import SwiftUI
import Combine

struct EditTextView: View {
    
    let hint: String
    @Binding var text: String
    let limit:Int
    var keyBoadrType = UIKeyboardType.default //default
        
    @Binding var errorMessage:LocalizedStringKey?
    
    var textChanged: (String) -> Void
    
    @State var isSelectedSSS:Bool = false
    
    var focusChangeListener: (Bool) -> Void = { _ in }

    var body: some View {
        VStack(spacing:0){
            TextField(
                hint,
                text: $text,
                onEditingChanged: { edit in
                    self.isSelectedSSS = edit
                    focusChangeListener(edit)
                })
            .font(Font.system(size: 16, weight: .regular, design: .default))
            .padding(16)
            .lineLimit(5)
            .background(
                RoundedRectangle(cornerRadius: 5)
                    .fill(AppColor.surface)
            )
            .overlay(
                RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                    .stroke(getRoundedColor(), lineWidth: 2)
            ).onReceive(Just(text)) { str in limitText(limit)
                textChanged(str)
            }
            .keyboardType(keyBoadrType)
            
            if let notNullErrorMessage = errorMessage {
                Text(notNullErrorMessage)
                    .bodySmall()
                    .foregroundColor(AppColor.error)
                    .frame(maxWidth:.infinity, alignment: .leading)
                    .padding(.top, 4)
                    .padding(.leading, 16)
            }
        }.environment(\.colorScheme, .light)
    }
    
    func getRoundedColor() -> Color {
        if (errorMessage != nil) {
            return AppColor.error
        }
        
        if (isSelectedSSS){
            return AppColor.primary
        }else{
            return AppColor.onSurfaceVariant
        }
    }
    
    func limitText(_ upper: Int) {
        if text.count > upper {
            text = String(text.prefix(upper))
        }
    }
}
