//
//  UpdateView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.03.2022.
//

import SwiftUI

struct UpdateView: View {
    var body: some View {
        VStack{
            Spacer()
            
            Image("NewVersion").resizable().frame(width: 200, height: 200)
            
            Text(Strings.MSG_UPDATE_GO_TO).multilineTextAlignment(.center)
            Spacer()
            //TODO make button
            NavigationLink(
                destination:CreateAddressView()
            ){
                Text(Strings.ACTION_UPDATE_UPDATE).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(8)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
            
        }.background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_NEW_VERSION)
        )
        
    }
}

struct UpdateView_Previews: PreviewProvider {
    static var previews: some View {
        UpdateView()
    }
}
