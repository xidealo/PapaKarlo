//
//  EditTextView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 11.04.2022.
//

import SwiftUI

struct EditTextView: View {
    
    let hint: String
    @Binding var text: String
    
    var body: some View {
        TextField(hint, text: $text)
            .padding().overlay(
                RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                    .stroke(Color("surfaceVariant"), lineWidth: 2)
            )
        
    }
}

struct EditTextView_Previews: PreviewProvider {
    static var previews: some View {
        EditTextView(hint: "hint", text: .constant(""))
    }
}
