//
//  ProductDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI
import Kingfisher
import shared

struct ProductDetailsView: View {
    
    let menuProductUuid:String
    let menuProductName:String
    let productDetailsOpenedFrom:ProductDetailsOpenedFrom
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool

    @State var viewModel = ProductDetailsViewModel(
        getMenuProductUseCase: iosComponent.provideGetMenuProductByUuidUseCase(),
        observeCartUseCase: iosComponent.provideObserveCartUseCase(),
        addCartProductUseCase: iosComponent.provideAddCartProductUseCase(),
        analyticService: iosComponent.provideAnalyticService(),
        editCartProductUseCase: iosComponent.provideEditCartProductUseCase(),
        getAdditionGroupsWithSelectedAdditionUseCase: iosComponent.provideGetAdditionGroupsWithSelectedAdditionUseCase(),
        getSelectedAdditionsPriceUseCase: iosComponent.provideGetPriceOfSelectedAdditionsUseCase()
    )
    //State
    @State var cartCostAndCount : CartCostAndCount? = nil
    @State var menuProduct : ProductDetailsStateDataState.MenuProduct? = nil
    @State var screenState : ProductDetailsStateDataState.ScreenState = ProductDetailsStateDataState.ScreenState.loading
    //-----
    
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    var body: some View {
        ZStack (alignment: .bottom){
            VStack(spacing: 0){
                ToolbarWithCartView(
                    title: LocalizedStringKey(menuProductName),
                    cost: cartCostAndCount?.cost,
                    count: cartCostAndCount?.count,
                    isShowLogo: .constant(false),
                    back: {
                        viewModel.onAction(action: ProductDetailsStateActionBackClick())
                    },
                    isRootActive: $isRootActive,
                    selection: $selection,
                    showOrderCreated: $showOrderCreated
                )

//                ScrollView{
//                        VStack(spacing:0){
//                            if let photoLink = menuProduct?.photoLink{
//                                KFImage(
//                                    URL(string: photoLink)
//                                )
//                                .resizable()
//                                .aspectRatio(contentMode: .fit)
//                            }
//                       
//                            Group{
//                                HStack(spacing:0){
//                                    Text(menuProduct?.name ?? "")
//                                        .titleMedium(weight: .bold)
//                                        .frame(maxWidth: .infinity, alignment: .leading)
//                                        .foregroundColor(AppColor.onSurface)
//
//                                    Text(menuProduct?.size ?? "")
//                                        .bodySmall()
//                                        .foregroundColor(AppColor.onSurfaceVariant)
//                                }
//                                .padding(.top, 12)
//
//                                HStack(spacing:0){
//                                    if let oldPrice = menuProduct?.oldPrice {
//                                        StrikeText(
//                                            text: oldPrice
//                                        )
//                                        .padding(.trailing, Diems.SMALL_RADIUS)
//                                    }
//                                    Text(menuProduct?.newPrice ?? "")
//                                        .foregroundColor(AppColor.onSurface)
//                                        .bodyLarge(weight: .bold)
//                                }
//                                .frame(maxWidth: .infinity, alignment: .leading)
//                                .padding(.top, 4)
//
//                                Text(menuProduct?.description_ ?? "")
//                                    .bodyLarge()
//                                    .frame(maxWidth: .infinity, alignment: .leading)
//                                    .padding(.top, 16)
//                                    .foregroundColor(AppColor.onSurface)
//                                    .padding(.bottom, 16)
//                            }
//                            .padding(.horizontal, Diems.MEDIUM_PADDING)
//                        }
//                        .background(AppColor.surface)
//                        .cornerRadius(Diems.MEDIUM_RADIUS)
//                        .padding(Diems.MEDIUM_PADDING)
//                        .padding(.bottom, 48)
//            }

            }

           Button(action: {
               viewModel.onAction(
                action: ProductDetailsStateActionAddProductToCartClick(productDetailsOpenedFrom: productDetailsOpenedFrom, cartProductUuid: nil)
               )
           }) {
               ButtonText(text: Strings.ACTION_PRODUCT_DETAILS_ADD)
           }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
    }
    
    func subscribe(){
        viewModel.onAction(
            action: ProductDetailsStateActionInit(menuProductUuid: menuProductUuid, selectedAdditionUuidList: [])
        )
        listener = viewModel.dataState.watch { productDetailsVM in
            if let productDetailsStateVM =  productDetailsVM {
                menuProduct = productDetailsStateVM.menuProduct
                screenState = productDetailsStateVM.screenState
                cartCostAndCount = productDetailsStateVM.cartCostAndCount
            }
        }
    }

    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                    let productDetailsStateEvents = events as? [ProductDetailsStateEvent] ?? []

                productDetailsStateEvents.forEach { event in
                        switch(event){
                        case is ProductDetailsStateEventNavigateBack : self.mode.wrappedValue.dismiss()
                        case is ProductDetailsStateEventNavigateToConsumerCart :                        print("ProductDetailsStateEventNavigateToConsumerCart")
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
    
    func unsubscribe(){
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}
