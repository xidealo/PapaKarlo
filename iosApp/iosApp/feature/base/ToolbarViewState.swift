//
//  ToolbarViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 03.07.2022.
//

import SwiftUI

class ToolbarViewState : NSObject {
    let count:String
    let cost:String
    
    init(count:String, cost: String){
        self.count = count
        self.cost = cost
    }
    
}
