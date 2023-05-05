//
//  ChangeCityViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 02.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ChangeCityViewModel: ObservableObject {
    
    @Published var changeCityViewState:ChangeCityViewState = ChangeCityViewState(
        cityList: [],
        changeCityState: ChangeCityState.loading
    )
    var cityList: [SelectableCity] = []
    
    init(){
        loadData()
    }
    
    func loadData(){
        iosComponent.provideCityInteractor().observeCityList().watch { arr in
            self.cityList = arr as! [SelectableCity]
            
            (self.changeCityViewState.copy() as! ChangeCityViewState).apply { newState in
                newState.cityList = (arr as! [SelectableCity]).map({ selectableCity in
                    ChangeCityItem(
                        id: selectableCity.city.uuid,
                        city: selectableCity.city.name,
                        isSelected: selectableCity.isSelected
                    )
                })
                newState.changeCityState = ChangeCityState.success
                self.changeCityViewState = newState
            }
            
        }
    }
    
    func selectCity(uuid:String){
        iosComponent.provideSaveSelectedCityUseCase().invoke(cityUuid: uuid) { err in
            if(err != nil){
                print(err ?? "empty err")
            }
            
            (self.changeCityViewState.copy() as! ChangeCityViewState).apply { newState in
                newState.changeCityState = ChangeCityState.back
                self.changeCityViewState = newState
            }
        }
    }
    
}

enum ChangeCityState{
    case loading, success, back
}
