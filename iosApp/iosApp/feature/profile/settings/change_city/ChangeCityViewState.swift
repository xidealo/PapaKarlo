//
//  ChangeCityViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 02.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class ChangeCityViewState : NSObject,  NSCopying  {
    
    var cityList:[ChangeCityItem]
    var changeCityState:ChangeCityState
    
    
    init(cityList:[ChangeCityItem], changeCityState:ChangeCityState){
        self.cityList = cityList
        self.changeCityState = changeCityState
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = ChangeCityViewState(
            cityList: cityList,
            changeCityState: changeCityState
        )
        return copy
    }
    
}
