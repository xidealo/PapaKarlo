//
//  ToolbarView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.04.2022.
//

import SwiftUI
import shared

struct ToolbarView: View {
    
    let title:String
    
    var logout: (() -> Void)? = nil
    var back: (() -> Void)? = nil
    
    var body: some View {
        HStack(spacing:0){
            Button(action: {
                back!()
            }) {
                if(back != nil){
                    Image(systemName: "arrow.backward")
                        .foregroundColor(Color("onSurface"))
                        .padding(.horizontal, Diems.SMALL_PADDING)
                }
            }.padding(Diems.SMALL_PADDING)
            
            Text(title)
                .foregroundColor(Color("onSurface"))
                .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .bold, design: .default))
                .padding(.vertical, Diems.MEDIUM_PADDING)
            Spacer()
            
            if logout != nil{
                Button(action:{
                    logout!()
                }){
                    Image("LogoutIcon")
                        .resizable()
                        .frame(width: 24, height: 24)
                        .padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurface"))
                }
            }
            
        }.background(Color("surface"))
    }
}
