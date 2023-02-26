//
//  ErrorView.swift
//  iosApp
//
//  Created by Марк Шавловский on 18.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ErrorView: View {
    
    @State var mainText:String
    @State var extratext:String
    
    var action: () -> Void

    var body: some View {
        VStack(spacing:0){
            Spacer()

            DefaultImage(width: 120, height:120, imageName: "ErrorImage")
            BoldText(text: mainText)
                .multilineTextAlignment(.center)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.top, 32)

            Text(extratext)
                .multilineTextAlignment(.center)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.top, Diems.SMALL_PADDING)

            Spacer()
            
            Button {
                action()
            } label: {
                Text(Strings.ACTION_RETRU)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.BUTTON_RADIUS)
            }
            .padding(.bottom, Diems.MEDIUM_PADDING)
            .padding(.horizontal, Diems.MEDIUM_PADDING)
        }
        .hiddenNavigationBarStyle()
        .background(Color("background"))
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(mainText: "Pupa", extratext: "Lupa") {
            
        }
    }
}
