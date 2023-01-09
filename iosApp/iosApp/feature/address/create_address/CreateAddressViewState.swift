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
    var hasHouseLengthError:Bool
    var hasFlatError:Bool
    var hasEntranceError:Bool
    var hasFloorError:Bool
    var hasCommentError:Bool
    var isLoading:Bool
    
    init(
        streetList:[StreetItem],
        isBack:Bool,
        hasStreetError:Bool,
        hasHouseError:Bool,
        hasHouseLengthError:Bool,
        hasFlatError:Bool,
        hasEntranceError:Bool,
        hasFloorError:Bool,
        hasCommentError:Bool,
        isLoading:Bool
    ){
        self.streetList = streetList
        self.isBack = isBack
        self.hasStreetError = hasStreetError
        self.hasHouseError = hasHouseError
        self.hasHouseLengthError = hasHouseLengthError
        self.hasFlatError = hasFlatError
        self.hasEntranceError = hasEntranceError
        self.hasFloorError = hasFloorError
        self.hasCommentError = hasCommentError
        self.isLoading = isLoading
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = CreateAddressViewState(
            streetList: streetList,
            isBack: isBack,
            hasStreetError: hasStreetError,
            hasHouseError: hasHouseError,
            hasHouseLengthError: hasHouseLengthError,
            hasFlatError:hasFlatError,
            hasEntranceError:hasEntranceError,
            hasFloorError:hasFloorError,
            hasCommentError:hasCommentError,
            isLoading:isLoading
        )
        return copy
    }
}
