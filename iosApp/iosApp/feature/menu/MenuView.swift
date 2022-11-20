//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI

struct MenuView: View {
    
    @ObservedObject private var viewModel = MenuViewModel()//viewModelStore.getMenuViewModel()
    @State var lastShowCategory = ""
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    var body: some View {
        VStack(spacing:0){
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
                            ForEach(viewModel.menuViewState.menuItems.indices){  i in
                                Section(
                                    header: LargeHeaderText(
                                        text:viewModel.menuViewState.menuItems[i].categorySectionItem.name
                                    ).id(viewModel.menuViewState.menuItems[i].categorySectionItem.id)
                                        .padding(.horizontal,  Diems.MEDIUM_PADDING)
                                        .padding(.top, Diems.MEDIUM_PADDING)){
                                            ForEach(viewModel.menuViewState.menuItems[i].categorySectionItem.menuProdctItems){ menuProductItem in
                                                NavigationLink(
                                                    destination:ProductDetailsView(menuProductUuid: menuProductItem.id)
                                                ){
                                                    MenuItemView(menuProductItem: menuProductItem, action: {
                                                        viewModel.addCartProductToCart(menuProductUuid: menuProductItem.id)
                                                    })
                                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                                    .padding(.vertical, Diems.HALF_SMALL_PADDING)
                                                }
                                                .onAppear(){
                                                    print("onAppear \(i)")
                                                    viewModel.checkAppear(index: i)
                                                }
                                                .onDisappear(){
                                                    print("onDisappear \(i)")
                                                    viewModel.checkDisappear(index: i)
                                                }
                                            }
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
        }
        .background(Color("background"))
        .navigationBarTitle("")
        .hiddenNavigationBarStyle()
        .preferredColorScheme(.light)
    }
}

struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView()
    }
}
