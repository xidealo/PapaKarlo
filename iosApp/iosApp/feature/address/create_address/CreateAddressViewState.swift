//
//  CreateAddressViewState.swift
//  iosApp
//
//  Created by Марк Шавловский on 23.07.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class CreateAddressViewState : NSObject {
    
    var streetList : [StreetItem]
    
    init(streetList:[StreetItem]){
        self.streetList = streetList
    }
    
}
