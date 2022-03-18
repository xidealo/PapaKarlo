//
//  UserAddressListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct UserAddressListView: View {
    let addressList: [AddressItem]
    
    var body: some View {
        VStack{
            //SuccessAddressListView(addressList: addressList)
            EmptyAddressListView()
            
        }
        .background(Color("background"))
        .navigationTitle(
            Text(Strings.TITLE_MY_ADDRESSES)
        )
        
    }
}

struct UserAddressListView_Previews: PreviewProvider {
    static var previews: some View {
        UserAddressListView(addressList: [AddressItem(id: UUID(), address: "Kimry")])
    }
}

struct SuccessAddressListView: View {
    let addressList: [AddressItem]

    var body: some View {
        ScrollView {
            LazyVStack{
                ForEach(addressList){ address in
                    AddressItemView(addressItem: address).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                    Text("")
                }
            }
        }.padding(.top, Diems.MEDIUM_PADDING)
    }
}

struct EmptyAddressListView: View {
    var body: some View {
        VStack{
            Spacer()
            
            Image("EmptyPage")
            
            Text(Strings.MSG_ADDRESS_LIST_EMPTY_ADDRESSES).multilineTextAlignment(.center)
            Spacer()
            
            NavigationLink(
                destination:CreateAddressView()
            ){
                Text(Strings.ACTION_ADDRESS_LIST_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(8)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}