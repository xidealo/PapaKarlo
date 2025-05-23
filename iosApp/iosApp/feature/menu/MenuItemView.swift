//
//  MenuItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 10.03.2022.
//

import Kingfisher
import shared
import SwiftUI

struct MenuItemView: View {
    let menuProductItem: MenuProductItem
    let productDetailsOpenedFrom: ProductDetailsOpenedFrom

    // for back after createOrder
    @State var openProductDetails: Bool = false
    @Binding var created: Bool
    @Binding var edited: Bool

    let action: () -> Void

    var body: some View {
        NavigationLink(
            destination: ProductDetailsView(
                menuProductUuid: menuProductItem.productUuid,
                menuProductName: menuProductItem.name,
                cartProductUuid: nil,
                additionUuidList: [],
                productDetailsOpenedFrom: productDetailsOpenedFrom,
                created: $created,
                edited: $edited
            ),
            isActive: $openProductDetails
        ) {
            VStack(spacing: 0) {
                KFImage(
                    URL(string: menuProductItem.photoLink)
                )
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(height: 110)

                VStack(spacing: 0) {
                    Text(menuProductItem.name)
                        .titleSmall(weight: .bold)
                        .lineLimit(1)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundColor(AppColor.onSurface)
                        .padding(.top, Diems.SMALL_PADDING)
                        .multilineTextAlignment(.leading)

                    HStack(spacing: 0) {
                        if let oldPrice = menuProductItem.oldPrice {
                            Text(String(oldPrice))
                                .strikethrough()
                                .bodySmall()
                                .foregroundColor(AppColor.onSurfaceVariant)
                                .padding(.trailing, Diems.SMALL_PADDING)
                        }
                        Text(menuProductItem.newPrice)
                            .bodySmall(weight: .bold)
                            .frame(maxWidth: .infinity, alignment: .topLeading)
                            .foregroundColor(AppColor.onSurface)
                    }
                    .padding(.top, 4)
                }
                .padding(.top, 8)
                .padding(.horizontal, 8)
                Button(
                    action: {
                        print(menuProductItem)
                        if menuProductItem.hasAdditions {
                            openProductDetails = true
                        } else {
                            action()
                        }
                    }) {
                        Text(Strings.ACTION_MENU_PRODUCT_WANT)
                            .labelLarge(weight: .medium)
                            .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40)
                            .foregroundColor(AppColor.primary)
                            .overlay(
                                RoundedRectangle(cornerRadius: Diems.BUTTON_RADIUS)
                                    .stroke(AppColor.primary, lineWidth: 2)
                            )
                            .padding(.horizontal, 8)
                    }
                    .padding(.top, 8)
                    .padding(.bottom, 8)
            }
            .frame(maxWidth: .infinity)
            .background(AppColor.surface)
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .shadow(color: Color.black.opacity(0.25), radius: 4, x: 0, y: 0)
        }.isDetailLink(false)
    }
}
