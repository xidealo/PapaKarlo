//
//  CreateAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 23.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class CreateAddressViewState : NSObject,  NSCopying {
    
    var streetList : [StreetItem]
    var isBack : Bool
    var hasStreetError: Bool
    var hasHouseError:Bool

    init(
        streetList:[StreetItem],
         isBack:Bool,
         hasStreetError:Bool,
         hasHouseError:Bool
    ){
        self.streetList = streetList
        self.isBack = isBack
        self.hasStreetError = hasStreetError
        self.hasHouseError = hasHouseError
    }
    
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = CreateAddressViewState(
            streetList: streetList,
            isBack: isBack,
            hasStreetError: hasStreetError,
            hasHouseError: hasHouseError
        )
        return copy
    }
}
