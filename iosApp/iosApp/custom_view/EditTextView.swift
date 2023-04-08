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
                    .padding()
                    .lineLimit(5)
                    .background(RoundedRectangle(cornerRadius: 5)
                    .fill(Color("surface")))
                    .overlay(
                        RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(Color("error"), lineWidth: 2)
                    ).onReceive(Just(text)) { str in limitText(limit)
                        textChanged(str)
                    }
                    .keyboardType(keyBoadrType)
                
                Text(errorMessage)
                    .foregroundColor(Color("error"))
                    .frame(maxWidth:.infinity, alignment: .leading)
            }else{
                TextField(hint, text: $text, onEditingChanged: { edit in
                    self.isSelectedSSS = edit
                })
                    .padding()
                    .lineLimit(5)
                    .background(RoundedRectangle(cornerRadius: 5).fill(Color("surface")))
                    .overlay(
                        isSelectedSSS ?
                        RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(Color("primary"), lineWidth: 2)
                        : RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                            .stroke(Color("onSurfaceVariant"), lineWidth: 2)
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
