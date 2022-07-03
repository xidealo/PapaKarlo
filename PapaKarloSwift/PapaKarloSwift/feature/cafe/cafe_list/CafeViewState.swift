//
//  CafeViewState.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 29.06.2022.
//

import Foundation


class CafeViewState :NSObject {
    
    let isLoading:Bool
    let cafeItemList:[CafeItem]
    
    init(isLoading:Bool, cafeItemList:[CafeItem]){
        self.isLoading = isLoading
        self.cafeItemList = cafeItemList
    }
    
    
}
