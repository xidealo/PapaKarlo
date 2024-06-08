//
//  ConsumerCartView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

import shared

struct ConsumerCartView: View {
    
    @State var viewModel: ConsumerCartViewModel = ConsumerCartViewModel(
        userInteractor: iosComponent.provideIUserInteractor(),
        cartProductInteractor: iosComponent.provideCartProductInteractor(),
        increaseCartProductCountUseCase: iosComponent.provideIncreaseCartProductCountUseCase(),
        addMenuProductUseCase: iosComponent.provideAddMenuProductUseCase(),
        removeCartProductUseCase: iosComponent.provideRemoveCartProductUseCase(),
        getRecommendationsUseCase: iosComponent.provideGetRecommendationsUseCase(),
        getMotivationUseCase: iosComponent.provideGetMotivationUseCaseUseCase(),
        analyticService: iosComponent.provideAnalyticService()
    )
    
    @State var consumerCartViewState = ConsumerCartViewState(
        state: ConsumerCartState.loading
    )
    
    @State var listener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    //State
    @State var openCreateOrder: Bool = false
    @State var openLogin: Bool = false
    @State var openProductDetails: Bool = false
    // ---
    
    //for back after createOrder
    @Binding var isRootActive: Bool
    @Binding var selection: Int
    @Binding var showOrderCreated: Bool
    //--
    
    //for add or edit product
    @State var created: Bool = false
    @State var edited: Bool = false
    //--
    
    //for back after createOrder
    @State private var selectedMenuProductUuid : String = ""
    @State private var selectedMenuProductName : String  = ""
    @State private var selectedCartProductItemIos : String = ""
    @State private var selectedAdditionUuidList : [String] = []
    //--
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "titleCartProducts",
                back: {
                    viewModel.onAction(action: ConsumerCartActionBackClick())
                }
            )
            NavigationLink(
                destination: LoginView(rootIsActive: $openLogin, isGoToCreateOrder: $openCreateOrder),
                isActive: $openLogin
            ){
                EmptyView()
            }
            .isDetailLink(false)
            
            NavigationLink(
                destination: CreateOrderView(
                    isRootActive: $isRootActive,
                    selection: $selection,
                    showOrderCreated: $showOrderCreated
                ),
                isActive: $openCreateOrder
            ){
                EmptyView()
            }
            
            NavigationLink(
                destination:
                    ProductDetailsView(
                        menuProductUuid: selectedMenuProductUuid,
                        menuProductName: selectedMenuProductName,
                        cartProductUuid: selectedCartProductItemIos,
                        additionUuidList: selectedAdditionUuidList,
                        productDetailsOpenedFrom: ProductDetailsOpenedFrom.cartProduct,
                        isRootActive: $isRootActive,
                        selection: $selection,
                        showOrderCreated: $showOrderCreated,
                        created: $created,
                        edited: $edited
                    ),
                isActive: $openProductDetails
            ){
                EmptyView()
            }
            
            switch consumerCartViewState.state {
            case .success(let cartProductItems, let consumerCartProducts, let bottomPanelInfo):
                ConsumerCartSuccessScreen(
                    cartProductItemUiList: cartProductItems,
                    recommendationProductList: consumerCartProducts,
                    bottomPanelInfoUi: bottomPanelInfo,
                    isRootActive: isRootActive,
                    selection: selection,
                    showOrderCreated: showOrderCreated,
                    created: $created,
                    edited: $edited,
                    action: viewModel.onAction
                )
            case .loading: LoadingView()
            case .error: ErrorView(
                mainText: "Что-то пошло не так",
                extratext: ""
            ){
                
            }
                
            }
        }
        .background(AppColor.background2)
        .hiddenNavigationBarStyle()
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Добавлено"),
                show: $created,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $created
        ).overlay(
            overlayView: ToastView(
                toast: Toast(title: "Изменено"),
                show: $edited,
                backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ),
            show: $edited
        )
    }
    
    func subscribe(){
        viewModel.onAction(action: ConsumerCartActionInit())
        listener = viewModel.dataState.watch { consumerCartStateVM in
            if let consumerCartStateVM =  consumerCartStateVM {
                switch consumerCartStateVM.state {
                case ConsumerCartDataState.State.loading : consumerCartViewState = ConsumerCartViewState(state: ConsumerCartState.loading)
                case ConsumerCartDataState.State.error : consumerCartViewState = ConsumerCartViewState(state: ConsumerCartState.error)
                case ConsumerCartDataState.State.success : consumerCartViewState =  ConsumerCartViewState(state: ConsumerCartState.success(
                    consumerCartStateVM.cartProductItemList.enumerated().map({ (index, cartProductItem) in
                        CartProductItemUi(
                            id: cartProductItem.uuid,
                            name: cartProductItem.name,
                            newCost: cartProductItem.newCost,
                            photoLink: cartProductItem.photoLink,
                            count: Int(cartProductItem.count),
                            isLast: index == consumerCartStateVM.cartProductItemList.count - 1
                        )
                    }),
                    consumerCartStateVM.recommendationList.map(
                        { menuProduct in
                            MenuProductItem(
                                id: menuProduct.uuid,
                                productUuid: menuProduct.uuid,
                                name: menuProduct.name,
                                newPrice: String(menuProduct.newPrice) + Strings.CURRENCY,
                                oldPrice: nil,
                                photoLink: menuProduct.photoLink,
                                hasAdditions: !menuProduct.hasAdditions
                            )
                        }
                    ),
                    getBottomPanelInfoUi(dataState: consumerCartStateVM)
                )
                )
                default:
                    consumerCartViewState =  ConsumerCartViewState(state: ConsumerCartState.error)
                }
            }
        }
    }
    
    func getBottomPanelInfoUi(dataState: ConsumerCartDataState) -> BottomPanelInfoUi? {
        if(dataState.cartProductItemList.isEmpty){
            return nil
        }
        print(dataState)
        
        let motivationUi: MotivationUi? = {
            if let warningItem = dataState.motivation {
                switch warningItem {
                case let minOrderCost as MotivationDataMinOrderCost:
                    return MotivationUi.MinOrderCost(minOrderCost.cost)
                case let forLowerDelivery as MotivationDataForLowerDelivery:
                    return MotivationUi.ForLowerDelivery(
                        forLowerDelivery.increaseAmountBy,
                        forLowerDelivery.progress,
                        forLowerDelivery.isFree
                    )
                case let lowerDeliveryAchieved as MotivationDataLowerDeliveryAchieved:
                    return MotivationUi.LowerDeliveryAchieved(lowerDeliveryAchieved.isFree)
                default:
                    return nil
                }
            }
            return nil
        }()
        
        return BottomPanelInfoUi(
            motivation: motivationUi,
            discount: dataState.discount,
            oldTotalCost: dataState.oldTotalCost,
            newTotalCost: dataState.newTotalCost
        )
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let consumerCartEvents = events as? [ConsumerCartEvent] ?? []
                
                consumerCartEvents.forEach { event in
                    print(event)
                    
                    switch(event){
                    case is ConsumerCartEventNavigateBack :
                        self.mode.wrappedValue.dismiss()
                    case is ConsumerCartEventNavigateToCreateOrder : openCreateOrder = true
                    case is ConsumerCartEventNavigateToLogin: openLogin = true
                    case is ConsumerCartEventNavigateToProduct:
                        let consumerCartEventNavigateToProduct = event as? ConsumerCartEventNavigateToProduct
                        
                        selectedMenuProductUuid = consumerCartEventNavigateToProduct?.uuid ?? ""
                        selectedMenuProductName = consumerCartEventNavigateToProduct?.name ?? ""
                        selectedCartProductItemIos = consumerCartEventNavigateToProduct?.cartProductUuid ?? ""
                        selectedAdditionUuidList = consumerCartEventNavigateToProduct?.additionUuidList ?? []
                        
                        openProductDetails = true
                    default:
                        print("def")
                    }
                }
                
                if !consumerCartEvents.isEmpty {
                    viewModel.consumeEvents(events: consumerCartEvents)
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

struct ConsumerCartSuccessScreen: View {
    
    let cartProductItemUiList : [CartProductItemUi]
    let recommendationProductList : [MenuProductItem]
    let bottomPanelInfoUi : BottomPanelInfoUi?
    
    //for back after createOrder
    @State var isRootActive : Bool
    @State var selection : Int
    @State var showOrderCreated : Bool
    
    //for add or edit product
    @Binding var created : Bool
    @Binding var edited : Bool
    
    let action: (ConsumerCartAction) -> Void
    
    let columns = [
        GridItem(.flexible(), spacing: 8, alignment: .top),
        GridItem(.flexible(), spacing: 8, alignment: .top)
    ]
    
    var body: some View {
        VStack(spacing:0){
            ZStack(alignment: .bottom){
                ScrollView {
                    LazyVStack(spacing:0){
                        
                        ForEach(cartProductItemUiList){ cartProductItemIos in
                            VStack(spacing:0){
                                Button {
                                    action(
                                        ConsumerCartActionOnCartProductClick(
                                            cartProductUuid: cartProductItemIos.id
                                        )
                                    )
                                } label: {
                                    CartProductView(
                                        cartProductItem: cartProductItemIos,
                                        plusAction: {
                                            action(
                                                ConsumerCartActionAddProductToCartClick(
                                                    cartProductUuid: cartProductItemIos.id
                                                )
                                            )
                                        },
                                        minusAction: {
                                            action(
                                                ConsumerCartActionRemoveProductFromCartClick(
                                                    cartProductUuid: cartProductItemIos.id
                                                )
                                            )
                                        })
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.bottom, 8)
                                    
                                }
                                
                                //   if(cartProductListIos.last?.id != cartProductItemIos.id){
                                //       Divider()
                                //           .frame(height: 2)
                                //           .overlay(AppColor.stroke)
                                //           .padding(.horizontal, Diems.MEDIUM_PADDING)
                                //           .padding(.bottom, 8)
                                //  }
                            }
                        }
                    }
                    
                    if(!recommendationProductList.isEmpty){
                        Text("consumer_cart_recommendations")
                            .titleMedium(weight: .medium)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                            .padding(.bottom, 8)
                            .padding(.top, 16)
                    }
                    
                    LazyVGrid(columns: columns, spacing: 8) {
                        ForEach(recommendationProductList){ menuProductItem in
                            MenuItemView(
                                menuProductItem: menuProductItem,
                                productDetailsOpenedFrom: ProductDetailsOpenedFrom.recommendationProduct,
                                isRootActive : $isRootActive,
                                selection : $selection,
                                showOrderCreated : $showOrderCreated,
                                created: $created,
                                edited: $edited,
                                action: {
                                    action(
                                        ConsumerCartActionAddRecommendationProductToCartClick(
                                            menuProductUuid: menuProductItem.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.bottom, 8)
                }
                
                VStack(spacing:0){
                    if let motivation = bottomPanelInfoUi?.motivation{
                        
                        VStack(spacing:0){
                            HStack(spacing:0){
                                getMotivationIcon(motivation: motivation)
                                getMotivationText(motivation: motivation)
                                    .foregroundColor(AppColor.onSurface)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .padding(.leading, 8)
                                
                            }
                            
                            if let progress = getMotivationProgress(motivation: motivation){
                                ProgressView(value: progress)
                                    .tint(progress < 1 ? AppColor.warning : AppColor.positive)
                                    .frame(height: 8)
                                    .progressViewStyle(LinearProgressViewStyle())
                                    .cornerRadius(4)
                                    .animation(.easeIn(duration: 3), value: progress)
                                    .padding(.top, 4)
                                
                                
                                
                                //                          .progressViewStyle(LinearProgressViewStyle(tint: getAnimatedColor(progress: progress)))
                                
                            }
                        }.padding(.horizontal, 16)
                            .padding(.top, 16)
                    }
                    
                    if let discount = bottomPanelInfoUi?.discount{
                        HStack(spacing:0){
                            Text("consumer_cart_discount")
                                .bodyMedium()
                                .foregroundColor(AppColor.onSurface)
                            
                            Spacer()
                            
                            DiscountCard(text:discount)
                        }.padding(.top, 8)
                            .padding(.horizontal, 16)
                    }
                    
                    HStack(spacing:0){
                        Text("consumer_cart_total")
                            .bodyMedium(weight: .bold)
                            .foregroundColor(AppColor.onSurface)
                        
                        Spacer()
                        
                        if let oldTotalCost = bottomPanelInfoUi?.oldTotalCost{
                            Text(oldTotalCost)
                                .strikethrough()
                                .bodyMedium(weight: .bold)
                                .foregroundColor(AppColor.onSurfaceVariant)
                                .padding(.trailing, 4)
                        }
                        
                        if let newTotalCost = bottomPanelInfoUi?.newTotalCost{
                            Text(newTotalCost)
                                .bodyMedium(weight: .bold)
                                .foregroundColor(AppColor.onSurface)
                        }
                    }.padding(.top, 8)
                        .padding(.horizontal, 16)
                    
                    Button {
                        action(ConsumerCartActionOnCreateOrderClick())
                    } label: {
                        ButtonText(text: Strings.ACTION_CART_PRODUCT_CREATE_ORDER)
                    }
                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                    .padding(.vertical, Diems.MEDIUM_PADDING)
                }.background(AppColor.surface)
            }
        }
    }
    
    func getMotivationProgress(motivation: MotivationUi) -> Float?  {
        switch(motivation){
        case .MinOrderCost(_):
            return nil
            
        case .ForLowerDelivery(_, let progress, _):
            return progress
            
        case .LowerDeliveryAchieved(_):
            return 1
        }
    }
    
    //    func getAnimatedColor(progress:Int) -> Color {
    //        let warningColor = AppColor.warning
    //        let positiveColor = AppColor.positive
    //          return Color(red: Double(1.0 - progress) * warningColor + Double(progress) * positiveColor,
    //                       green: Double(1.0 - progress) * warningColor + Double(progress) * positiveColor,
    //                       blue: Double(1.0 - progress) * warningColor. + Double(progress) * positiveColor)
    //      }
    //
    func getMotivationText(motivation: MotivationUi) -> some View  {
        switch(motivation){
        case .MinOrderCost(let cost):
            return (
                Text("Минимальая сумма заказа: ").bodySmall() + Text(cost).bodySmall(weight: .bold)
            )
            
        case .ForLowerDelivery(let increaseAmountBy, _, let isFree):
            return isFree ? (Text("Добавте еще на ") + Text(increaseAmountBy).bold() + Text(" для бесплатной доставки")).bodySmall() : (Text("Добавте еще на ") + Text(increaseAmountBy).bold() + Text(" для уменьшения стоимости доставки")).bodySmall()
        case .LowerDeliveryAchieved(let isFree):
            return Text(isFree ? "Бесплатная доставка" : "Уменьшенная стоимость доставки").bodySmall()
        }
    }
    
    func getMotivationIcon(motivation: MotivationUi) -> some View  {
        switch(motivation){
        case .MinOrderCost(_):
            return IconImage(imageName: "ic_warning")
                .foregroundColor(AppColor.warning)
            
        case .ForLowerDelivery(_, _, _):
            return IconImage(imageName: "ic_delivery")
                .foregroundColor(AppColor.onSurfaceVariant)
            
        case .LowerDeliveryAchieved(_):
            return IconImage(imageName: "ic_delivery")
                .foregroundColor(AppColor.onSurfaceVariant)
        }
    }
    
    struct ConsumerCartEmptyScreen: View {
        @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
        
        @Binding var isRootActive:Bool
        @Binding var selection:Int
        
        var body: some View {
            VStack(spacing:0){
                Spacer()
                
                EmptyWithIconView(
                    imageName:  "CartIcon",
                    title: "emptyCartTitleProfile",
                    secondText: "emptyCartSecondProfile"
                )
                
                Spacer()
                
                Button {
                    isRootActive = false
                    selection = 1
                } label: {
                    ButtonText(text: Strings.ACTION_CART_PRODUCT_MENU)
                }.padding(Diems.MEDIUM_PADDING)
            }
        }
    }
}
