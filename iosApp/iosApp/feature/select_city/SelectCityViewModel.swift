//
//  SelectCityViewModel.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import Foundation
import shared

class SelectCityViewModel : ObservableObject {
    
    @Published var selectCityViewState:SelectCityViewState = SelectCityViewState(
        isLoading: false,
        cityList: [],
        isGoToMenu: false
    )
    
    init(){
        selectCityViewState = SelectCityViewState(isLoading: true, cityList: selectCityViewState.cityList, isGoToMenu: false)
        iosComponent.provideCityInteractor().getCityList(completionHandler: {
            cityList, error in
            self.selectCityViewState = SelectCityViewState(isLoading: false, cityList: cityList?.map({ city in
                CityItem(city : city)
            }) ?? [], isGoToMenu: false)
        })
    }
    
    func saveSelectedCity(city:City){
        (selectCityViewState.copy() as!SelectCityViewState).apply { newState in
            newState.isGoToMenu = true
            selectCityViewState = newState
        }
        iosComponent.provideCityInteractor().saveSelectedCity(city: city, completionHandler: { err in })
    }
}

class SelectCityViewState:NSObject, NSCopying{
    var isLoading:Bool
    var cityList : [CityItem]
    var isGoToMenu:Bool
    
    init(isLoading: Bool, cityList: [CityItem], isGoToMenu:Bool) {
        self.isLoading = isLoading
        self.cityList = cityList
        self.isGoToMenu = isGoToMenu
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = SelectCityViewState(isLoading: isLoading, cityList: cityList, isGoToMenu: isGoToMenu)
            return copy
        }
    
}
