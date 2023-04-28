//
//  AddressItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct AddressItemView: View {
    
    let addressItem:AddressItem
    
    var body: some View {
        SelectableElementCard(text: addressItem.address, isSelected: addressItem.isSelected)
    }
}

struct AddressItemView_Previews: PreviewProvider {
    static var previews: some View {
        AddressItemView(
            addressItem:
                AddressItem(
                    id: "12",
                    address: "Kimry 21",
                    isClickable: false,
                    isSelected: false
                )
        )
    }
}
