//
//  CityItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 25.02.2022.
//

import SwiftUI
import shared

struct CityItemView: View {
    var city: CityItem

    var body: some View {
        ElementCard(text: city.city.name).padding(.top, 2)
    }
}

class CityItemView_Previews: PreviewProvider {
    static var previews: some View {
        CityItemView(city:CityItem(city: City(uuid: "uuidd", name: "Dubna", timeZone: "+3")))
    }
}
