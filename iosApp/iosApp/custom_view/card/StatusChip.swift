//
//  StatusChip.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.03.2022.
//

import SwiftUI

struct StatusChip: View {
    let status: String

    var body: some View {
        Text(status)
            .foregroundColor(AppColor.surface)
            .frame(height: 30)
            .padding(Diems.SMALL_PADDING)
            .background(Color.blue) // select color
            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .heavy, design: .default))
            .cornerRadius(Diems.MEDIUM_RADIUS)
    }
}

struct StatusChip_Previews: PreviewProvider {
    static var previews: some View {
        StatusChip(status: "PREPATING")
    }
}
