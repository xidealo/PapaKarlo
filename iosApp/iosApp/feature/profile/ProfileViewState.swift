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
    var profieState: ProfileState
    var actionList: [ProfileAction]
    
    init(
        userUuid:String,
        hasAddresses:Bool,
        lastOrder:OrderItem?,
        profileState:ProfileState,
        actionList: [ProfileAction]
    ){
        self.userUuid = userUuid
        self.hasAddresses = hasAddresses
        self.lastOrder = lastOrder
        self.profieState = profileState
        self.actionList = actionList
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = ProfileViewState(
            userUuid: userUuid,
            hasAddresses: hasAddresses,
            lastOrder: lastOrder,
            profileState: profieState,
            actionList: actionList
        )
        return copy
    }
}

enum ProfileState{
    case notAuthorize, loading, success
}
