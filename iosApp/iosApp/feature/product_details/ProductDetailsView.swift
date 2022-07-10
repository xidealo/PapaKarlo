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
    
    @ObservedObject private var viewModel:ProductDetailsViewModel
    let menuProductUuid:String
    
    init(menuProductUuid:String) {
        self.viewModel = ProductDetailsViewModel(productUuid: menuProductUuid)
        self.menuProductUuid = menuProductUuid
    }
    
    var body: some View {
        VStack{
            ToolbarView(title: viewModel.productDetailsViewState.name, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count, isShowBackArrow: true, isCartVisible: true, isLogoutVisible: false)
            
            VStack{
                KFImage(URL(string: viewModel.productDetailsViewState.imageLink))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                
                Group{
                    HStack{
                        Text(viewModel.productDetailsViewState.name).frame(maxWidth: .infinity, alignment: .leading).font(.system(size: Diems.LARGE_TEXT_SIZE, weight: .heavy, design: .default))
                        Text(viewModel.productDetailsViewState.size).font(.system(size: Diems.SMALL_TEXT_SIZE, weight: .thin, design: .default))
                    }.padding(.top, Diems.MEDIUM_PADDING)
                    
                    HStack{
                        if viewModel.productDetailsViewState.oldPrice != nil{
                            StrikeText(text: String(viewModel.productDetailsViewState.oldPrice!))
                        }
                        Text(viewModel.productDetailsViewState.newPrice)
                            .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default))
                    }.frame(maxWidth: .infinity, alignment: .leading).padding(.top, Diems.SMALL_PADDING)
                    
                    Text(viewModel.productDetailsViewState.description).frame(maxWidth: .infinity, alignment: .leading).padding(.top, Diems.MEDIUM_PADDING).padding(.bottom, Diems.MEDIUM_PADDING)
                    
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
        .navigationBarHidden(true)
    }
}

struct ProductDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        ProductDetailsView(menuProductUuid: "")
    }
}
