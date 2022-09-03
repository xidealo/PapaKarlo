//
//  CafeViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation


class CafeViewState :NSObject,  NSCopying {
    
    var isLoading:Bool
    var cafeItemList:[CafeItem]
    
    init(isLoading:Bool, cafeItemList:[CafeItem]){
        self.isLoading = isLoading
        self.cafeItemList = cafeItemList
    }
    
    func copy(with zone: NSZone? = nil) -> Any {
        let copy = CafeViewState(isLoading: isLoading, cafeItemList: cafeItemList)
        return copy
    }
}
