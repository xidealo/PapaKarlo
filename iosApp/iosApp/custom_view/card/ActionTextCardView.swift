//
//  ActionTextCardView.swift
//  iosApp
//
//  Created by Марк Шавловский on 14.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ActionTextCardView: View {
    
    let icon:String?
    let placeHolder:String
    let text:String
    let action: () -> Void
    
    var body: some View {
        
        Button(action: action){
            HStack{
                VStack{
                    PlaceholderText(text:placeHolder).frame(maxWidth:.infinity, alignment: .leading)
                    Text(text)
                        .frame(maxWidth:.infinity, alignment: .leading)
                        .foregroundColor(Color("onSurface"))
                        .multilineTextAlignment(.leading)
                }.frame(maxWidth:.infinity, alignment: .leading)
                Image(systemName:"chevron.right").foregroundColor(Color("onSurfaceVariant"))
            }.padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.vertical, Diems.SMALL_PADDING)
                .background(Color("surface")).cornerRadius(Diems.MEDIUM_RADIUS)
        }
    }
}
