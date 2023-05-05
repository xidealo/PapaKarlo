//
//  CafeItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//
import SwiftUI
import Foundation
import shared

struct CafeItemUi:Identifiable {
    let id:String
    let address:String
    let workingHours:String
    let cafeOpenState: CafeItem.CafeOpenState
    let phone:String
    let latitude:Float
    let longitude:Float
}

