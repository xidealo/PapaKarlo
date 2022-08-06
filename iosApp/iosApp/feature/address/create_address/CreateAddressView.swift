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
    
    @ObservedObject private var viewModel = CreateAddressViewModel()
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        
        VStack{
            ToolbarView(title: Strings.TITLE_CREATION_ADDRESS, cost: "220 R", count: "2",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            VStack{
                SearchEditTextView(hint: Strings.HINT_CREATION_ADDRESS_STREET, text: $street, limit: 100, list: $viewModel.createAddressViewState.streetList).padding(.top, Diems.MEDIUM_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                
                Group{
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_HOUSE, text: $house, limit: 100)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_FLAT, text: $flat, limit: 5)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_ENTRANCE, text: $entarance, limit: 5)
                    EditTextView(hint: Strings.HINT_CREATION_ADDRESS_FLOOR, text: $floor, limit: 5)
                }.padding(.horizontal, Diems.MEDIUM_PADDING)
                
                EditTextView(hint: Strings.HINT_CREATION_ADDRESS_COMMENT, text: $comment, limit: 5).padding(.horizontal, Diems.MEDIUM_PADDING).padding(.bottom, Diems.SMALL_PADDING)

            }
            .padding(Diems.MEDIUM_PADDING)
            
            Spacer()
            
            Button(action: {
                viewModel.onCreateAddressClicked(streetName: street, house: house, flat: flat, entrance: entarance, floor: floor, comment: comment){ isBack in
                    if(isBack){
                        presentationMode.wrappedValue.dismiss()
                    }
                }
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
