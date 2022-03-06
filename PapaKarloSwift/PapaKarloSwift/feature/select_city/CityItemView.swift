//
//  CityItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 25.02.2022.
//

import SwiftUI

struct CityItemView: View {
    var city: CityItem

    var body: some View {
        Text(city.city).padding().frame(maxWidth: .infinity, alignment: .leading).background(Color.white).cornerRadius(15)
    }
}

class CityItemView_Previews: PreviewProvider {
    static var previews: some View {
        CityItemView(city:CityItem(city: "Kimry"))
    }
}
