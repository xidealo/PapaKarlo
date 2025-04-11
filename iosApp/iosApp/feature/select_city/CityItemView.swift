//
//  CityItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 25.02.2022.
//

import shared
import SwiftUI

struct CityItemView: View {
    var city: CityItem

    var body: some View {
        ElementCard(text: city.city.name)
    }
}

class CityItemView_Previews: PreviewProvider {
    static var previews: some View {
        CityItemView(city: CityItem(city: City(uuid: "uuidd", name: "Dubna", timeZone: "+3")))
    }
}
