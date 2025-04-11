//
//  IconImage.swift
//  iosApp
//
//  Created by Марк Шавловский on 03.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct IconImage: View {
    var width: CGFloat = 24
    var height: CGFloat = 24

    let imageName: String

    var body: some View {
        Image(imageName)
            .resizable()
            .renderingMode(.template)
            .frame(width: width, height: height)
    }
}

struct IconImage_Previews: PreviewProvider {
    static var previews: some View {
        IconImage(imageName: "icon")
    }
}
