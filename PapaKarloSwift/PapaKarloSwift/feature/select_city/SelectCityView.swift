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
            ToolbarView(title: Strings.TITLE_SELECT_CITY_CITY,  cost: "220 R", count: "2", isShowBackArrow: false, isCartVisible: false, isLogoutVisible: false)
            
            ScrollView {
                LazyVStack{
                    ForEach(cities){ city in
                        NavigationLink(
                            destination:ContainerView()
                        ){
                            CityItemView(city: city).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                    }
                }
            }.padding(.top, Diems.MEDIUM_PADDING)
        }
        .background(Color("background"))
        .navigationBarHidden(true)
        
    }
}

struct SelectCityView_Previews: PreviewProvider {
    static var previews: some View {
        SelectCityView()
    }
}
