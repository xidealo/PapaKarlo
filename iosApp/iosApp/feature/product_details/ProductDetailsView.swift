//
//  ProductDetailsView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI
import Kingfisher

extension String {
    
    func load() -> UIImage {
        do {
            guard let url = URL(string: self) else {
                return UIImage()
            }
            let data:Data = try Data(contentsOf: url)
            return UIImage(data: data) ?? UIImage()
        }catch {
            return UIImage()
        }
    }
}

struct ProductDetailsView: View {
    
    @StateObject private var viewModel:ProductDetailsViewModel
    let menuProductUuid:String
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    init(menuProductUuid:String, isRootActive: Binding<Bool>, selection: Binding<Int>, showOrderCreated: Binding<Bool> ) {
        self._viewModel = StateObject(wrappedValue: ProductDetailsViewModel(productUuid: menuProductUuid))
        self.menuProductUuid = menuProductUuid
        self._isRootActive = isRootActive
        self._selection = selection
        self._showOrderCreated =  showOrderCreated
    }
    
    var body: some View {
        ZStack (alignment: .bottom){
            VStack(spacing: 0){
                ToolbarWithCartView(
                    title: LocalizedStringKey(viewModel.productDetailsViewState.name),
                    cost: viewModel.toolbarViewState.cost,
                    count: viewModel.toolbarViewState.count,
                    isShowLogo: .constant(false),
                    back: {
                        self.mode.wrappedValue.dismiss()
                    },
                    isRootActive: $isRootActive,
                    selection: $selection,
                    showOrderCreated: $showOrderCreated
                )
                
                ScrollView{
                        VStack(spacing:0){
                            KFImage(
                                URL(string: viewModel.productDetailsViewState.imageLink)
                            )
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            
                            Group{
                                HStack(spacing:0){
                                    Text(viewModel.productDetailsViewState.name)
                                        .titleMedium(weight: .bold)
                                        .frame(maxWidth: .infinity, alignment: .leading)
                                        .foregroundColor(AppColor.onSurface)
                                    
                                    Text(viewModel.productDetailsViewState.size)
                                        .bodySmall()
                                        .foregroundColor(AppColor.onSurfaceVariant)
                                }
                                .padding(.top, 12)
                                
                                HStack(spacing:0){
                                    if viewModel.productDetailsViewState.oldPrice != nil{
                                        StrikeText(
                                            text: String(viewModel.productDetailsViewState.oldPrice!) + Strings.CURRENCY
                                        )
                                        .padding(.trailing, Diems.SMALL_RADIUS)
                                    }
                                    Text(viewModel.productDetailsViewState.newPrice)
                                        .foregroundColor(AppColor.onSurface)
                                        .bodyLarge(weight: .bold)
                                }
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .padding(.top, 4)
                                
                                Text(viewModel.productDetailsViewState.description)
                                    .bodyLarge()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .padding(.top, 16)
                                    .foregroundColor(AppColor.onSurface)
                                    .padding(.bottom, 16)
                            }
                            .padding(.horizontal, Diems.MEDIUM_PADDING)
                        }
                        .background(AppColor.surface)
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .padding(Diems.MEDIUM_PADDING)
                        .padding(.bottom, 48)
            }
            
            }
            
            Button(action: {
                viewModel.addCartProductToCart(menuProductUuid: menuProductUuid)
            }) {
                ButtonText(text: Strings.ACTION_PRODUCT_DETAILS_ADD)
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.subscribeOnFlow()
        }
        .onDisappear(){
            viewModel.unsubFromFlows()
        }
    }
}
