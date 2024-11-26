//
//  CafeItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//
import SwiftUI
import Foundation
import shared

struct CafeItem: Identifiable {
    let id: String
    let address: String
    let phone: String
    let workingHours: String
    let cafeOpenState: CafeOpenState
    let latitude: Float
    let longitude: Float
}

