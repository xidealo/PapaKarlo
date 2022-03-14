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
            ScrollView {
                LazyVStack{
                    ForEach(addressList){ address in
                        AddressItemView(addressItem: address).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                        Text("")
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
            Text("")
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
