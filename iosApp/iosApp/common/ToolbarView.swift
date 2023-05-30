//
//  ToolbarView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 05.04.2022.
//

import SwiftUI
import shared

struct ToolbarView: View {
    
    let title:LocalizedStringKey
    
    var logout: (() -> Void)? = nil
    var back: (() -> Void)? = nil
    @State private var showingAlert = false
    
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
                .foregroundColor(AppColor.onSurface)
                .titleMedium(weight: .bold)
                .lineLimit(1)
                .padding(.vertical, Diems.MEDIUM_PADDING)
            
            Spacer()
            
            if logout != nil{
                Button(action:{
                    showingAlert = true
                }){
                    Image("LogoutIcon")
                        .resizable()
                        .frame(width: 24, height: 24)
                        .padding(Diems.MEDIUM_PADDING).foregroundColor(Color("onSurface"))
                }.alert("Выйти из профиля?", isPresented: $showingAlert) {
                    Button("Выйти") {
                        logout!()
                    }
                    Button("Отмена", role: .cancel) { }
                }
            }
            
        }.background(Color("surface"))
    }
}
