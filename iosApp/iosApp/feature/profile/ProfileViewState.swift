//
//  ProfileViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

class ProfileViewState : NSObject,  NSCopying  {
    
    var userUuid: String
    var hasAddresses: Bool
    var lastOrder: OrderItem?
    var profieState:ProfileState
    
    init(userUuid:String, hasAddresses:Bool, lastOrder:OrderItem?, profileState:ProfileState){
        self.userUuid = userUuid
        self.hasAddresses = hasAddresses
        self.lastOrder = lastOrder
        self.profieState = profileState
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = ProfileViewState(
            userUuid: userUuid,
            hasAddresses: hasAddresses,
            lastOrder: lastOrder,
            profileState: profieState
        )
        return copy
    }
}

enum ProfileState{
    case notAuthorize, loading, success
}
