//
//  CafeItem.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//
import SwiftUI
import Foundation

struct CafeItem:Identifiable {
    let id:String
    let address:String
    let workingHours:String
    let isOpenMessage:String
    let isOpenColor:Color
    let phone:String
    let latitude:Float
    let longitude:Float
}

