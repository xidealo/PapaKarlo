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
        hasHouseError: false,
        hasHouseLengthError: false,
        hasFlatError: false,
        hasEntranceError: false,
        hasFloorError: false,
        hasCommentError: false,
        isLoading:false
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
                hasHouseError: false,
                hasHouseLengthError: false,
                hasFlatError : false,
                hasEntranceError : false,
                hasFloorError : false,
                hasCommentError : false,
                isLoading:false
            )
        }
    }
    
    func onCreateAddressClicked(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        floor: String,
        comment: String,
        action: @escaping (_ isBack:Bool) -> Void){
            
            (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                newState.hasStreetError = false
                newState.hasHouseError = false
                newState.hasFlatError = false
                newState.hasEntranceError = false
                newState.hasFloorError = false
                newState.hasCommentError = false
                newState.isLoading = true
                createAddressViewState = newState
            }
            
            if(!createAddressViewState.streetList.contains(where: {streetName == $0.name})){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasStreetError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(house.isEmpty){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasHouseError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(house.count > 5){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasHouseLengthError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(flat.count > 5){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasFlatError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(entrance.count > 5){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasEntranceError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(floor.count > 5){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasFlatError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            if(comment.count > 100){
                (createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                    newState.hasCommentError = true
                    newState.isLoading = false
                    createAddressViewState = newState
                }
                return
            }
            
            iosComponent.provideIAddressInteractor().createAddress(streetName: streetName, house: house, flat: flat, entrance: entrance, comment: comment, floor: floor) { userAddress, err in
                if(userAddress == nil){
                    (self.createAddressViewState.copy() as! CreateAddressViewState).apply { newState in
                        newState.isLoading = false
                        self.createAddressViewState = newState
                    }
                    action(false)
                }else{
                    action(true)
                }
            }
        }
}
