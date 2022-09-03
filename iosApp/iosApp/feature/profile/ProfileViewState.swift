//
//  ProfileViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 04.07.2022.
//

import Foundation
import shared

struct ProfileViewState{
    let userUuid: String
    let hasAddresses: Bool
    let lastOrder: OrderItem?
    let isAuthorize:Bool
    let isLoading:Bool
}
