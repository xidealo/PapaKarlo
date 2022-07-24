//
//  CreateAddressViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 23.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class CreateAddressViewModel : ObservableObject {
    
    @Published var createAddressViewState:CreateAddressViewState = CreateAddressViewState(streetList: [])
    
    init(){
        iosComponent.provideIStreetInteractor().getStreetList { streetList, err in
            self.createAddressViewState = CreateAddressViewState(streetList: streetList?.map({ street in
                StreetItem(id: street.uuid, name: street.name, cityUuid: street.cityUuid)
            }) ?? [])
        }
    }
    
    
    func onCreateAddressClicked(streetName: String, house: String, flat: String, entrance: String, floor: String, comment: String){
        
        iosComponent.provideIAddressInteractor().createAddress(streetName: streetName, house: house, flat: flat, entrance: entrance, comment: comment, floor: floor) { userAddress, err in
            if(userAddress == nil){
                //show error
            }else{
                //show nice message and back
            }
        }
        
    }
}
