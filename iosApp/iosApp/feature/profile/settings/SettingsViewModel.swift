//
//  SettingsViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared


class SettingsViewModel: ObservableObject {
    @Published var settingsViewState:SettingsViewState = SettingsViewState(
        phone: "",
        email: nil,
        city: ""
    )
    
    init(){
//        iosComponent.provideISettingsInteractor().getSettings { settings, err in
//            self.settingsViewState = SettingsViewState(phone: settings?.user.phone ?? "" , email: settings?.user.email , city: settings?.cityName ?? "")
//        }
    }
    
    
    func disableUser(completion: @escaping(Bool) -> Void){
        iosComponent.provideDisableUserUseCase().invoke { err in
            if(err != nil){
                print(err)
                completion(false)
            }else{
                completion(true)
            }
        }
    }
}
