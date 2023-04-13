//
//  CafeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI

struct CafeItemView: View {

    var cafeItem: CafeItem

    var body: some View {
        VStack(spacing:0){
            Text(cafeItem.address)
                .bodyMedium()
                .foregroundColor(AppColor.onSurface)
                .padding(.horizontal,  Diems.MEDIUM_PADDING)
                .padding(.top, 12)
                .frame(maxWidth: .infinity, alignment: .leading)

            HStack(spacing:0){
                Text(cafeItem.workingHours)
                    .foregroundColor(AppColor.onSurfaceVariant)
                    .labelMedium()
                Text(cafeItem.isOpenMessage)
                    .foregroundColor(cafeItem.isOpenColor)
                    .labelMedium()
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.bottom, 12)
            .padding(.top, 8)
            .frame(maxWidth: .infinity, alignment: .leading)
            
        }.background(AppColor.surface)
            .cornerRadius(15)
    }
}

struct CafeView_Previews: PreviewProvider {
    static var previews: some View {
        CafeItemView(
            cafeItem: CafeItem(id: "UUID", address: "Kimry chapaevo 22a", workingHours: "9:00 - 22:00", isOpenMessage: "Open", isOpenColor: Color.green, phone: "8999999999", latitude: 0, longitude: 0
            )
        )
    }
}
