//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    @ObservedObject private var viewModel = viewModelStore.getMenuViewModel()
    
    var body: some View {
        VStack(spacing:0){
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
                    LazyVStack(spacing:0){
                        ForEach(viewModel.menuViewState.menuItems){ menuItem in
                            Section(header: LargeHeaderText(text: menuItem.categorySectionItem.name)){
                                ForEach(menuItem.categorySectionItem.menuProdctItems){ menuProductItem in
                                    NavigationLink(
                                        destination:ProductDetailsView(menuProductUuid: menuProductItem.id)
                                    ){
                                        MenuItemView(menuProductItem: menuProductItem, action: {
                                            viewModel.addCartProductToCart(menuProductUuid: menuProductItem.id)
                                        }).padding(.horizontal, Diems.MEDIUM_PADDING).padding(.vertical, Diems.HALF_SMALL_PADDING)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            BottomBarView(isSelected: 1)
        }.background(Color("background"))
            .navigationBarTitle("")
            .navigationBarHidden(true)
    }
}


struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
