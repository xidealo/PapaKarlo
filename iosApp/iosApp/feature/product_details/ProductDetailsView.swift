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
        VStack{
            ToolbarWithCartView(
                title: viewModel.productDetailsViewState.name,
                cost: viewModel.toolbarViewState.cost,
                count: viewModel.toolbarViewState.count,
                back: {
                    self.mode.wrappedValue.dismiss()
                },
                isRootActive: $isRootActive,
                selection: $selection,
                showOrderCreated: $showOrderCreated
            )
            
            VStack{
                KFImage(URL(string: viewModel.productDetailsViewState.imageLink))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                
                Group{
                    HStack{
                        Text(viewModel.productDetailsViewState.name)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .foregroundColor(Color("onSurface"))
                            .font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
                        Text(viewModel.productDetailsViewState.size)
                            .foregroundColor(Color("onSurface"))
                            .font(.system(size: Diems.SMALL_TEXT_SIZE, weight: .thin, design: .default))
                    }.padding(.top, Diems.SMALL_PADDING)
                    
                    HStack{
                        if viewModel.productDetailsViewState.oldPrice != nil{
                            StrikeText(text: String(viewModel.productDetailsViewState.oldPrice!) + Strings.CURRENCY)
                        }
                        Text(viewModel.productDetailsViewState.newPrice )
                            .foregroundColor(Color("onSurface"))
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default))
                    }.frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.top, Diems.SMALL_PADDING)
                    
                    Text(viewModel.productDetailsViewState.description)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.top, Diems.SMALL_PADDING)
                        .padding(.bottom, Diems.MEDIUM_PADDING)
                        .foregroundColor(Color("onSurface"))
                    
                }.padding(.horizontal, Diems.MEDIUM_PADDING)
            }
            .background(Color("surface"))
            .cornerRadius(Diems.MEDIUM_RADIUS)
            .padding(Diems.MEDIUM_PADDING)
            
            Spacer()
            
            Button(action: {
                viewModel.addCartProductToCart(menuProductUuid: menuProductUuid)
            }) {
                Text(Strings.ACTION_PRODUCT_DETAILS_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
            viewModel.subscribeOnFlow()
        }
        .onDisappear(){
            viewModel.unsubFromFlows()
        }
    }
}
