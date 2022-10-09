//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    @ObservedObject private var viewModel = viewModelStore.getMenuViewModel()
    @State var lastShowCategory = ""
    
    var body: some View {
        VStack(spacing:0){
            ToolbarView(title: Strings.TITLE_MENU, cost: viewModel.toolbarViewState.cost, count: viewModel.toolbarViewState.count,  isShowBackArrow: false, isCartVisible: true, isLogoutVisible: false)
            
            if viewModel.menuViewState.isLoading {
                LoadingView()
            }else{
                ScrollView(.horizontal, showsIndicators:false) {
                    ScrollViewReader{ scrollReader in
                        HStack(spacing:0){
                            ForEach(viewModel.menuViewState.categoryItemModels){ categoryItemModel in
                                CategoryItemView(
                                    categoryItemModel: categoryItemModel,
                                    action: viewModel.seletTagWithScroll
                                )
                                .padding(.horizontal, Diems.HALF_SMALL_PADDING)
                                .id(categoryItemModel.id)
                            }
                        }
                        .onChange(of: viewModel.menuViewState, perform: { menuState in
                            print("select horizontal tag")
                            print(menuState.scrollToPostion)
                                withAnimation(.spring()){
                                    scrollReader.scrollTo(menuState.scrollToHorizontalPostion)
                                }
                        })
                    }
                }.padding(.vertical, Diems.SMALL_PADDING)
                
                ScrollView {
                    ScrollViewReader{ scrollReader in
                        LazyVStack(spacing:0){
                            ForEach(viewModel.menuViewState.menuItems){ menuItem in
                                Section(
                                    header: LargeHeaderText(
                                        text:menuItem.categorySectionItem.name
                                    ).id(menuItem.categorySectionItem.id)
                                        .padding(.horizontal,  Diems.MEDIUM_PADDING)
                                        .padding(.top, Diems.MEDIUM_PADDING)){
                                            ForEach(menuItem.categorySectionItem.menuProdctItems){ menuProductItem in
                                                NavigationLink(
                                                    destination:ProductDetailsView(menuProductUuid: menuProductItem.id)
                                                ){
                                                    MenuItemView(menuProductItem: menuProductItem, action: {
                                                        viewModel.addCartProductToCart(menuProductUuid: menuProductItem.id)
                                                    })
                                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                                    .padding(.vertical, Diems.HALF_SMALL_PADDING)
                                                }
                                            }
                                        }
                                        .onAppear(){
                                            print("show tag")
                                            print(menuItem.categorySectionItem.name)
                                            viewModel.selectTagWithHorizontalScroll(tagName: menuItem.categorySectionItem.name)
                                        }
                            }
                        }.onChange(of: viewModel.menuViewState, perform: { menuState in
                            if(menuState.scrollToPostion != lastShowCategory){
                                self.lastShowCategory = menuState.scrollToPostion
                                withAnimation(.spring()){
                                    scrollReader.scrollTo(menuState.scrollToPostion, anchor: .top)
                                }
                            }
                        })
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
