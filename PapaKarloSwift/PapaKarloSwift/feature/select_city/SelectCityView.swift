//
//  SelectCity.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct SelectCityView: View {
    let cities = [CityItem(city: "Kimry"), CityItem(city: "Dubna")]
    
    var body: some View {
        List {
            ForEach(cities){ city in
                CityItemView(city: city)
            }
        }
    }
}

struct SelectCityView_Previews: PreviewProvider {
    static var previews: some View {
        SelectCityView()
    }
}
