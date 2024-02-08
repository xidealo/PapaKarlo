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
    @State var text: String
    let limit:Int
    
    @State var list:[StreetItem]
    
    @State var prevSimbol = ""
    
    @Binding var hasError:Bool
    @State var errorMessage:String = ""
    
    var textChanged: (String) -> Void
    
    var body: some View {
        VStack (spacing:0) {
            EditTextView(
                hint: hint,
                text: text,
                limit: limit,
                hasError: $hasError,
                errorMessage: errorMessage,
                textChanged: textChanged
            ).onReceive(Just(text)) { str in
                limitText(limit)
            }
            
            ForEach(list){ street in
                Button {
                    prevSimbol = street.name
                    text = street.name
                } label: {
                    Text(street.name)
                        .padding(Diems.SMALL_PADDING)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(AppColor.surface)
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .foregroundColor(AppColor.onSurface)
                        .padding(.top, Diems.SMALL_PADDING)
                }
            }
        }
    }
    
    func limitText(_ upper: Int) {
        if text.count > upper {
            text = String(text.prefix(upper))
        }
    }
}
