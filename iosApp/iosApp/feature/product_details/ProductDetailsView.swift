//
//  ProductDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import Kingfisher
import shared
import SwiftUI

struct ProductDetailsView: View {
    let menuProductUuid: String
    let menuProductName: String
    let cartProductUuid: String?
    let additionUuidList: [String]

    let productDetailsOpenedFrom: ProductDetailsOpenedFrom

    @Binding var created: Bool
    @Binding var edited: Bool

    @State var viewModel = ProductDetailsViewModel(
        getMenuProductUseCase: iosComponent.provideGetMenuProductByUuidUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase(),
        addCartProductUseCase: iosComponent.provideAddCartProductUseCase(),
        analyticService: iosComponent.provideAnalyticService(),
        editCartProductUseCase: iosComponent.provideEditCartProductUseCase(),
        getAdditionGroupsWithSelectedAdditionUseCase: iosComponent.provideGetAdditionGroupsWithSelectedAdditionUseCase(),
        getSelectedAdditionsPriceUseCase: iosComponent.provideGetPriceOfSelectedAdditionsUseCase()
    )

    @State var productDetailsViewState = ProductDetailsViewState(
        photoLink: "",
        name: "",
        size: "",
        oldPrice: nil,
        newPrice: "",
        priceWithAdditions: "",
        description: "",
        additionList: [],
        screenState: ProductDetailsStateDataState.ScreenState.loading
    )

    // -----

    // Listeners
    @State var listener: Closeable?
    @State var eventsListener: Closeable?
    // -----

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        ZStack(alignment: .bottom) {
            VStack(spacing: 0) {
                ToolbarView(
                    title: LocalizedStringKey(menuProductName),
                    back: {
                        viewModel.onAction(action: ProductDetailsStateActionBackClick())
                    }
                )

                ScrollView {
                    LazyVStack(spacing: 0) {
                        KFImage(
                            URL(string: productDetailsViewState.photoLink)
                        )
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(Diems.MEDIUM_RADIUS)

                        Group {
                            HStack(spacing: 0) {
                                Text(productDetailsViewState.name)
                                    .titleMedium(weight: .bold)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .foregroundColor(AppColor.onSurface)

                                Text(productDetailsViewState.size)
                                    .bodySmall()
                                    .foregroundColor(AppColor.onSurfaceVariant)
                            }
                            .padding(.top, 12)

                            HStack(spacing: 0) {
                                if let oldPrice = productDetailsViewState.oldPrice {
                                    StrikeText(
                                        text: oldPrice
                                    )
                                    .padding(.trailing, Diems.SMALL_RADIUS)
                                }

                                Text(productDetailsViewState.newPrice)
                                    .foregroundColor(AppColor.onSurface)
                                    .bodyLarge(weight: .bold)
                            }
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.top, 4)

                            Text(productDetailsViewState.description)
                                .bodyLarge()
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .padding(.top, 16)
                                .foregroundColor(AppColor.onSurface)

                            ForEach(productDetailsViewState.additionList) { additionItem in
                                switch additionItem {
                                case let addition as AdditionItem.AdditionHeaderItem: Text(addition.name)
                                    .titleMedium(weight: .medium)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .padding(.bottom, 8)
                                    .padding(.top, 16)
                                case let addition as AdditionItem.AdditionListItem:
                                    Button(action: {
                                        viewModel.onAction(
                                            action: ProductDetailsStateActionAdditionClick(
                                                uuid: addition.product.uuid,
                                                groupUuid: addition.product.groupId
                                            )
                                        )
                                    }) {
                                        VStack(spacing: 0) {
                                            HStack(spacing: 0) {
                                                KFImage(
                                                    URL(string: addition.product.photoLink)
                                                )
                                                .resizable()
                                                .frame(width: 40, height: 40)
                                                .aspectRatio(contentMode: .fit)
                                                .cornerRadius(Diems.MEDIUM_RADIUS)

                                                Text(addition.product.name)
                                                    .bodyLarge()
                                                    .padding(.leading, 8)
                                                    .foregroundColor(AppColor.onSurface)

                                                Spacer()

                                                if let price = addition.product.price {
                                                    Text(price)
                                                        .bodyLarge()
                                                        .padding(.trailing, 8)
                                                        .foregroundColor(AppColor.onSurface)
                                                }

                                                if addition.isMultiple {
                                                    ZStack {
                                                        IconImage(
                                                            width: 20,
                                                            height: 20,
                                                            imageName: addition.product.isSelected ? "ic_enabled_checkbox" : "ic_disabled_checkbox"
                                                        )
                                                        .foregroundColor(
                                                            addition.product.isSelected ? AppColor.primary : AppColor.onSurfaceVariant
                                                        )

                                                        if addition.product.isSelected {
                                                            IconImage(width: 12, height: 10, imageName: "CheckIcon")
                                                                .foregroundColor(
                                                                    AppColor.onPrimary
                                                                )
                                                        }
                                                    }
                                                } else {
                                                    IconImage(
                                                        width: 24,
                                                        height: 24,
                                                        imageName: addition.product.isSelected ? "ic_enabled_radiobutton" : "ic_disabled_radiobutton"
                                                    ).foregroundColor(
                                                        addition.product.isSelected ? AppColor.primary : AppColor.onSurfaceVariant
                                                    )
                                                }
                                            }.frame(maxWidth: .infinity, alignment: .leading)

                                            if !addition.product.isLast {
                                                FoodDeliveryDivider()
                                                    .padding(.vertical, 8)
                                            }
                                        }
                                    }
                                default: EmptyView()
                                }
                            }
                        }
                    }
                    .padding(Diems.MEDIUM_PADDING)
                    .padding(.bottom, 64)
                }
            }

            FoodDeliveryExtendedFab(
                text: Strings.ACTION_PRODUCT_DETAILS_ADD + productDetailsViewState.priceWithAdditions,
                onClick: {
                    viewModel.onAction(
                        action: ProductDetailsStateActionAddProductToCartClick(
                            productDetailsOpenedFrom: productDetailsOpenedFrom,
                            cartProductUuid: cartProductUuid
                        )
                    )
                },
                icon: "ic_plus",
                iconBadge: nil,
                maxWidth: .infinity
            )
            .padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(AppColor.surface)
        .hiddenNavigationBarStyle()
        .onAppear {
            subscribe()
            eventsSubscribe()
        }
        .onDisappear {
            unsubscribe()
        }
    }

    func subscribe() {
        viewModel.onAction(
            action: ProductDetailsStateActionInit(
                menuProductUuid: menuProductUuid,
                selectedAdditionUuidList: additionUuidList
            )
        )
        listener = viewModel.dataState.watch { productDetailsVM in
            if let productDetailsStateVM = productDetailsVM {
                productDetailsViewState = ProductDetailsViewState(
                    photoLink: productDetailsStateVM.menuProduct.photoLink,
                    name: productDetailsStateVM.menuProduct.name,
                    size: productDetailsStateVM.menuProduct.size,
                    oldPrice: getOldPrice(menuProduct: productDetailsStateVM.menuProduct),
                    newPrice: "\(productDetailsStateVM.menuProduct.newPrice)\(productDetailsStateVM.menuProduct.currency)",
                    priceWithAdditions: "\(productDetailsStateVM.menuProduct.priceWithAdditions)\(productDetailsStateVM.menuProduct.currency) ",
                    description: productDetailsVM?.menuProduct.description_ ?? "",
                    additionList: getAdditionItemList(menuProduct: productDetailsStateVM.menuProduct),
                    screenState: productDetailsStateVM.screenState
                )
            }
        }
    }

    func getOldPrice(menuProduct: ProductDetailsStateDataState.MenuProduct) -> String? {
        if menuProduct.oldPrice == nil {
            return nil
        } else {
            return "\(menuProduct.oldPrice ?? 0)\(menuProduct.currency)"
        }
    }

    func getAdditionItemList(menuProduct: ProductDetailsStateDataState.MenuProduct) -> [AdditionItem] {
        var additionItemList: [AdditionItem] = []

        for additionGroup in menuProduct.additionGroups {
            additionItemList.append(
                AdditionItem.AdditionHeaderItem(id: additionGroup.uuid, name: additionGroup.name)
            )
            for (index, addition) in additionGroup.additionList.enumerated() {
                additionItemList.append(AdditionItem.AdditionListItem(
                    id: addition.uuid,
                    product: MenuProductAdditionItem(
                        uuid: addition.uuid,
                        isSelected: addition.isSelected,
                        name: addition.name,
                        price: getAdditionPrice(addition: addition),
                        isLast: index == additionGroup.additionList.endIndex - 1,
                        photoLink: addition.photoLink,
                        groupId: additionGroup.uuid
                    ),
                    isMultiple: !additionGroup.singleChoice
                )
                )
            }
        }

        return additionItemList
    }

    func getAdditionPrice(addition: Addition) -> String? {
        if addition.price == nil {
            return nil
        } else {
            return "+\(addition.price ?? 0)\(Strings.CURRENCY)"
        }
    }

    func eventsSubscribe() {
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events {
                let productDetailsStateEvents = events as? [ProductDetailsStateEvent] ?? []

                for event in productDetailsStateEvents {
                    switch event {
                    case is ProductDetailsStateEventNavigateBack: self.mode.wrappedValue.dismiss()
                    case is ProductDetailsStateEventNavigateToConsumerCart: print("ProductDetailsStateEventNavigateToConsumerCart")
                    case is ProductDetailsStateEventAddedProduct:
                        created = true
                        self.mode.wrappedValue.dismiss()
                    case is ProductDetailsStateEventEditedProduct:
                        edited = true
                        self.mode.wrappedValue.dismiss()
                    default:
                        print("def")
                    }
                }

                if !productDetailsStateEvents.isEmpty {
                    viewModel.consumeEvents(events: productDetailsStateEvents)
                }
            }
        })
    }

    func unsubscribe() {
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}
