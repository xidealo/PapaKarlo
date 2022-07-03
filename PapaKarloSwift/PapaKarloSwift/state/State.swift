//
//  State.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import Foundation

enum StateUI<T> {
    
    case loading
    case success(T)
    case empty
    case error(String)
    
}
