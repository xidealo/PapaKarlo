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
    
    //@Binding var isShowError:Bool
    
    var body: some View {
        //if(isShowError){
            TextField(hint, text: $text)
                .padding()
                .lineLimit(5)
                .background(RoundedRectangle(cornerRadius: 5).fill(Color("surface")))
                .overlay(
                    RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                        .stroke(Color("surfaceVariant"), lineWidth: 2)
                ).onReceive(Just(text)) { _ in limitText(limit) }
            .keyboardType(keyBoadrType)
      //  }

    }
    
    func limitText(_ upper: Int) {
          if text.count > upper {
              text = String(text.prefix(upper))
          }
      }
}

struct EditTextView_Previews: PreviewProvider {
    static var previews: some View {
        EditTextView(hint: "hint", text: .constant(""), limit: 100)
    }
}
