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
    
    //-----
    
    //Listeners
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    //-----
    
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
                
                ScrollView{
                    LazyVStack(spacing:0){
                        KFImage(
                            URL(string: productDetailsViewState.photoLink)
                        )
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        
                        Group{
                            HStack(spacing:0){
                                Text(productDetailsViewState.name)
                                    .titleMedium(weight: .bold)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .foregroundColor(AppColor.onSurface)
                                
                                Text(productDetailsViewState.size)
                                    .bodySmall()
                                    .foregroundColor(AppColor.onSurfaceVariant)
                            }
                            .padding(.top, 12)
                            
                            HStack(spacing:0){
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
                            
                            ForEach(productDetailsViewState.additionList){ additionItem in
                                switch(additionItem){
                                case let addition as AdditionItem.AdditionHeaderItem : Text(addition.name)
                                        .titleMedium(weight: .medium)
                                        .frame(maxWidth: .infinity, alignment: .leading)
                                        .padding(.bottom, 8)
                                        .padding(.top, 16)
                                case let addition as AdditionItem.AdditionListItem : VStack(spacing:0){
                                    HStack(spacing:0){
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
                                        
                                        Spacer()
                                        
                                        if let price = addition.product.price{
                                            Text(price)
                                                .bodyLarge()
                                        }
                                    
                                        Toggle(isOn: .constant(addition.product.isSelected)){
                                            
                                        }
                                        
                                    }.frame(maxWidth: .infinity, alignment: .leading)
                                    
                                    Divider()
                                        .padding(.vertical, 8)
                                }
                                default : EmptyView()
                                }
                            }
                            
                        }
                    }
                    .padding(Diems.MEDIUM_PADDING)
                    .padding(.bottom, 48)
                }
            }
            
            Button(action: {
                viewModel.onAction(
                    action: ProductDetailsStateActionAddProductToCartClick(
                        productDetailsOpenedFrom: productDetailsOpenedFrom,
                        cartProductUuid: nil
                    )
                )
            }) {
                ButtonText(text: Strings.ACTION_PRODUCT_DETAILS_ADD)
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(AppColor.surface)
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
            if let productDetailsStateVM = productDetailsVM {
                productDetailsViewState =  ProductDetailsViewState(
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
                cartCostAndCount = productDetailsStateVM.cartCostAndCount
            }
        }
    }
    
    func getOldPrice(menuProduct:ProductDetailsStateDataState.MenuProduct) -> String? {
        if menuProduct.oldPrice == nil {
            return nil
        }else{
            return "\(menuProduct.oldPrice ?? 0)\(menuProduct.currency)"
        }
    }
    
    func getAdditionItemList(menuProduct:ProductDetailsStateDataState.MenuProduct) -> [AdditionItem] {
        var additionItemList : [AdditionItem] = []
        
        menuProduct.additionGroups.forEach { additionGroup in
            additionItemList.append(
                AdditionItem.AdditionHeaderItem(id: additionGroup.uuid, name: additionGroup.name)
            )
            additionGroup.additionList.forEach { addition in
                additionItemList.append(AdditionItem.AdditionListItem(
                    id: addition.uuid,
                    product: MenuProductAdditionItem(
                        uuid: addition.uuid,
                        isSelected: addition.isSelected,
                        name: addition.name,
                        price: getAdditionPrice(addition: addition),
                        isLast: false,
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
    
    func getAdditionPrice(addition:Addition) -> String? {
        if addition.price == nil {
            return nil
        }else{
            return "+\(addition.price ?? 0)\(Strings.CURRENCY)"
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
