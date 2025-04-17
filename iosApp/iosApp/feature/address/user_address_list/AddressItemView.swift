//
//  AddressItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct AddressItemView: View {
    let addressItem: AddressItem

    var body: some View {
        SelectableElementCard(
            text: addressItem.address,
            isSelected: addressItem.isSelected,
            isEnabled: addressItem.isEnabled
        )
    }
}
