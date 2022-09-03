//
//  DefaultImage.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import SwiftUI

struct DefaultImage: View {
    var width:CGFloat = 200
    var height:CGFloat = 200

    let imageName:String
    var body: some View {
        Image(imageName)
            .resizable()
            .frame(width: width, height: height)
    }
}

struct DefaultImage_Previews: PreviewProvider {
    static var previews: some View {
        DefaultImage(imageName: "EmptyPage")
    }
}
