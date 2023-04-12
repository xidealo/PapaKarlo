//
//  ChangeCityItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.04.2022.
//

import SwiftUI

struct ChangeCityItemView: View {
    var city: ChangeCityItem

    var body: some View {
        SelectableElementCard(text: city.city, isSelected: city.isSelected)
    }
}

struct ChangeCityItemView_Previews: PreviewProvider {
    static var previews: some View {
        ChangeCityItemView(city: ChangeCityItem(id: "", city: "Kimry", isSelected: true))
    }
}
