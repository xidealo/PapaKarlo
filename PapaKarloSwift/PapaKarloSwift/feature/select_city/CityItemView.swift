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
        Text(city.city).frame(maxWidth: .infinity).background(Color.white).cornerRadius(15)
    }
}

struct CityItemView_Previews: PreviewProvider {
    static var previews: some View {
        CityItemView(city:CityItem(city: "Kimry"))
    }
}
