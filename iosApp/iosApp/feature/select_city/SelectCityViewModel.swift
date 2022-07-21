//
//  SelectCityViewModel.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import Foundation
import shared

class SelectCityViewModel : ObservableObject {
    
    @Published var selectCityViewState:SelectCityViewState = SelectCityViewState(isLoading: false, cityList: [])

    init(){
        selectCityViewState = SelectCityViewState(isLoading: true, cityList: selectCityViewState.cityList)
        iosComponent.provideCityInteractor().getCityList(completionHandler: {
            cityList, error in
            self.selectCityViewState = SelectCityViewState(isLoading: false, cityList: cityList?.map({ city in
                CityItem(city : city)
            }) ?? [])
        })
    }
    
    func saveSelectedCity(city:City){
        iosComponent.provideCityInteractor().saveSelectedCity(city: city, completionHandler: { _, error in })
    }
}

class SelectCityViewState:NSObject{
    var isLoading:Bool
    var cityList : [CityItem]
    
    init(isLoading: Bool, cityList: [CityItem] ) {
        self.isLoading = isLoading
        self.cityList = cityList
    }
}
