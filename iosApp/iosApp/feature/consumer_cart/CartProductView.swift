//
//  CartProductView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 26.03.2022.
//

import Kingfisher
import shared
import SwiftUI

struct CartProductView: View {
    let cartProductItem: CartProductItemUi

    // ----

    // Actions
    let plusAction: () -> Void
    let minusAction: () -> Void
    // ----

    var body: some View {
        HStack(alignment: .top, spacing: 0) {
            KFImage(URL(string: cartProductItem.photoLink))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: Diems.IMAGE_ELEMENT_WIDTH, maxHeight: Diems.IMAGE_ELEMENT_HEIGHT)
                .cornerRadius(Diems.MEDIUM_RADIUS)

            VStack(spacing: 0) {
                Text(cartProductItem.name)
                    .titleSmall(weight: .bold)
                    .lineLimit(nil)
                    .frame(maxWidth: .infinity, alignment: .topLeading)
                    .foregroundColor(AppColor.onSurface)

                if let additions = cartProductItem.additions {
                    Text(additions)
                        .bodySmall()
                        .lineLimit(nil)
                        .multilineTextAlignment(.leading)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurfaceVariant)
                        .padding(.top, 4)
                }

                HStack(alignment: .top, spacing: 0) {
                    if let oldCost = cartProductItem.oldCost {
                        Text(oldCost)
                            .strikethrough()
                            .bodySmall()
                            .foregroundColor(AppColor.onSurfaceVariant)
                    }

                    Text(cartProductItem.newCost)
                        .bodySmall(weight: .bold)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .foregroundColor(AppColor.onSurface)
                        .padding(.leading, (cartProductItem.oldCost != nil) ? 4 : 0)

                    CountPicker(
                        count: String(cartProductItem.count),
                        plusAction: plusAction,
                        minusAction: minusAction
                    )
                }
                .padding(.top, 4)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.leading, Diems.SMALL_PADDING)
        }.frame(maxWidth: .infinity, alignment: .topLeading)
    }
}
