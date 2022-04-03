//
//  SelectCity.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct SelectCityView: View {
    let cities : [CityItem]
    
    init() {
        cities = [CityItem(city: "Kimry"), CityItem(city: "Dubna")]
    }
    
    var body: some View {
            VStack{
                ScrollView {
                    LazyVStack{
                        ForEach(cities){ city in
                            NavigationLink(
                                destination:MenuView()
                            ){
                                CityItemView(city: city).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                            }
                        }
                    }
                }.padding(.top, Diems.MEDIUM_PADDING)
            }
            .background(Color("background"))
            .navigationTitle(
                Text(Strings.TITLE_SELECT_CITY_CITY)
            )
    }
}

struct SelectCityView_Previews: PreviewProvider {
    static var previews: some View {
        SelectCityView()
    }
}
