//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    @ObservedObject private var viewModel = MenuViewModel()
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_MENU, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count,  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            
            if viewModel.menuViewState.isLoading {
                LoadingView()
            }else{
                ScrollView(.horizontal, showsIndicators:false) {
                    HStack{
                        ForEach(viewModel.menuViewState.categoryItemModels){ categoryItemModel in
                            CategoryItemView(categoryItemModel: categoryItemModel)
                        }
                    }
                }.padding(.top, Diems.MEDIUM_PADDING)
                
                ScrollView {
                    LazyVStack{
                        ForEach(viewModel.menuViewState.menuItems){ menuItem in
                            Section(header: LargeHeaderText(text: menuItem.categorySectionItem.name)){
                                ForEach(menuItem.categorySectionItem.menuProdctItems){ menuProductItem in
                                    NavigationLink(
                                        destination:ProductDetailsView(menuProductUuid: menuProductItem.id)
                                    ){
                                        MenuItemView(menuProductItem: menuProductItem, action: {
                                            viewModel.addCartProductToCart(menuProductUuid: menuProductItem.id)
                                        }).padding(.bottom, Diems.SMALL_PADDING).padding(.horizontal, Diems.MEDIUM_PADDING)
                                    }
                                }
                            }
                        }
                    }
                }.padding(.top, Diems.MEDIUM_PADDING)
            }
        }.background(Color("background"))
        
    }
}


struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
