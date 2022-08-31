//
//  SpalshViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class SplashViewModel:ObservableObject {
    
    @Published var splashViewState:SplashViewState = SplashViewState(splashState: .checking)
    
    init(){
        iosComponent.provideCityInteractor().checkIsCitySelected { isSelected, err in
            
            guard let guardIsSelected = isSelected as? Bool else{
                return
            }
            
            if(guardIsSelected){
                self.splashViewState = SplashViewState(splashState: .isGoMenu)
            }else{
                self.splashViewState = SplashViewState(splashState: .isGoSelectCity)
            }
        }
    }
}
