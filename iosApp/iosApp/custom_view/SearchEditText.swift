//
//  SearchEditText.swift
//  iosApp
//
//  Created by Марк Шавловский on 24.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

import SwiftUI
import Combine

struct SearchEditTextView: View {
    
    let hint: String
    @Binding var text: String
    let limit:Int
    
    @Binding var list:[StreetItem]
    
    @State var prevSimbol = ""
    
    @Binding var hasError:Bool
    @State var errorMessage:String = ""
    
    var textChanged: (String) -> Void

    var body: some View {
        VStack{
            EditTextView(
                hint: hint,
                text: $text,
                limit: limit,
                hasError: $hasError,
                errorMessage: errorMessage,
                textChanged: textChanged
            )
                .onReceive(Just(text)) { str in
                    limitText(limit)
                }
            
            LazyVStack{
                ForEach(list){ street in
                    Button {
                        prevSimbol = street.name
                        text = street.name
                    } label: {
                        Text(street.name)
                            .padding(Diems.SMALL_PADDING)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .background(Color("surface"))
                            .cornerRadius(Diems.MEDIUM_RADIUS)
                            .foregroundColor(Color("onSurface"))
                    }
                }
            }.padding(.horizontal, Diems.MEDIUM_PADDING)
        }
    }
    
    func limitText(_ upper: Int) {
        if text.count > upper {
            text = String(text.prefix(upper))
        }
    }
}
