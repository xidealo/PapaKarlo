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
    
    @Binding var hasError:Bool
        
    @State var errorMessage:String = "Ошибка"
    
    var textChanged: (String) -> Void

    @State var isSelectedSSS:Bool = false
    var body: some View {
        VStack{
            if(hasError){
                TextField(hint, text: $text)
                    .font(Font.system(size: 16, weight: .regular, design: .default))
                    .padding(16)
                    .lineLimit(5)
                    .background(RoundedRectangle(cornerRadius: 5)
                    .fill(AppColor.surface))
                    .overlay(
                        RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(AppColor.error, lineWidth: 2)
                    ).onReceive(Just(text)) { str in limitText(limit)
                        textChanged(str)
                    }
                    .keyboardType(keyBoadrType)
                
                Text(errorMessage)
                    .bodySmall()
                    .foregroundColor(AppColor.error)
                    .frame(maxWidth:.infinity, alignment: .leading)
            }else{
                TextField(hint, text: $text, onEditingChanged: { edit in
                    self.isSelectedSSS = edit
                })
                    .font(Font.system(size: 16, weight: .regular, design: .default))
                    .padding(16)
                    .lineLimit(5)
                    .background(
                        RoundedRectangle(cornerRadius: 5)
                        .fill(AppColor.surface)
                    )
                    .overlay(
                        isSelectedSSS ?
                        RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(AppColor.primary, lineWidth: 2)
                        : RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(AppColor.onSurfaceVariant, lineWidth: 2)
                    ).onReceive(Just(text)) { str in limitText(limit)
                        textChanged(str)
                    }
                    .keyboardType(keyBoadrType)
            }
        }.environment(\.colorScheme, .light)
        
    }
    
    func limitText(_ upper: Int) {
        if text.count > upper {
            text = String(text.prefix(upper))
        }
    }
}
