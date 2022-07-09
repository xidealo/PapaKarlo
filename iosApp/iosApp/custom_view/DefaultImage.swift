//
//  DefaultImage.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI

struct DefaultImage: View {
    
    let imageName:String
    var body: some View {
        Image(imageName).resizable().frame(width: 200, height: 200)
    }
}

struct DefaultImage_Previews: PreviewProvider {
    static var previews: some View {
        DefaultImage(imageName: "EmptyPage")
    }
}
