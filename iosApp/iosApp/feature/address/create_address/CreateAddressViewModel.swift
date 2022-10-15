//
//  CreateAddressViewModel.swift
//  iosApp
//
//  Created by Марк Шавловский on 23.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class CreateAddressViewModel : ObservableObject {
    
    @Published var createAddressViewState:CreateAddressViewState = CreateAddressViewState(
        streetList: [],
        isBack: false,
        hasStreetError: false,
        hasHouseError: false
    )
    @Published var isBack : Bool = false

    init(){
        iosComponent.provideIStreetInteractor().getStreetList { streetList, err in
            self.createAddressViewState = CreateAddressViewState(
                streetList: streetList?.map({ street in
                StreetItem(id: street.uuid, name: street.name, cityUuid: street.cityUuid)
            }) ?? [],
                isBack: false,
                hasStreetError: false,
                hasHouseError: false
            )
        }
    }
    
    func onCreateAddressClicked(streetName: String, house: String, flat: String, entrance: String, floor: String, comment: String, action: @escaping (_ isBack:Bool) -> Void){
        
        if(!createAddressViewState.streetList.contains(where: {streetName == $0.name})){
            (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                newState.hasStreetError = true
                newState.hasHouseError = false
                createAddressViewState = newState
            }
            return
        }
        
        if(house.isEmpty){
            (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                newState.hasHouseError = true
                newState.hasStreetError = false
                createAddressViewState = newState
            }
            return
        }
        
        iosComponent.provideIAddressInteractor().createAddress(streetName: streetName, house: house, flat: flat, entrance: entrance, comment: comment, floor: floor) { userAddress, err in
            if(userAddress == nil){
                action(false)
            }else{
                action(true)
            }
        }
    }
}
