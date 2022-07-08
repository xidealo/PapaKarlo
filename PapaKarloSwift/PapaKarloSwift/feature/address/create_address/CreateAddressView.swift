//
//  CreateAddressView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 16.03.2022.
//

import SwiftUI

struct CreateAddressView: View {
    @State var street:String = ""
    @State var house:String = ""
    @State var flat:String = ""
    @State var entarance:String = ""
    @State var floor:String = ""
    @State var comment:String = ""
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_CREATION_ADDRESS, cost: "220 R", count: "2",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            VStack{
                Group{
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_STREET, text: $street).padding(.top, Diems.MEDIUM_PADDING)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_HOUSE, text: $house)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_FLAT, text: $flat)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE, text: $entarance)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_FLOOR, text: $floor)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_COMMENT, text: $comment).padding(.bottom, Diems.MEDIUM_PADDING)
                }.padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .padding(Diems.MEDIUM_PADDING)
           
            
            Spacer()
            
            Button(action: {
                print("button pressed")
            }) {
                Text(Strings.ACTION_CREATION_ADDRESS_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
            
        }.navigationBarHidden(true).background(Color("background"))
    }
}

struct CreateAddressView_Previews: PreviewProvider {
    static var previews: some View {
        CreateAddressView()
    }
}
