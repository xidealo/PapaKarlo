//
//  SelectCityViewModel.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import Foundation
import shared

class SelectCityViewModel : ObservableObject{
    
    init(){
            
//        iosComponent.provideCityInteractor().getCityList(completionHandler: { cityList,error    in
//            print(cityList ?? "NIL")
//        })
       
        iosComponent.provideCityInteractor().getCityList(completionHandler: {
            cityList, error in
            print(cityList)
        })
        
    }

}
