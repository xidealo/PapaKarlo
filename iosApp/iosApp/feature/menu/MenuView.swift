//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI
import shared

struct MenuView: View {
    
    @StateObject private var viewModel = MenuViewModel()
    @State var lastShowCategory = ""
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    //for back after createOrder
    @Binding var isRootActive:Bool
    @Binding var selection:Int
    @Binding var showOrderCreated:Bool
    
    let columns = [
        GridItem(.flexible(), spacing: 8, alignment: .top),
        GridItem(.flexible(), spacing: 8, alignment: .top)
      ]
    
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
                }
                .padding(.vertical, Diems.SMALL_PADDING)
                .background(AppColor.surface)
           
                ScrollView {
                    ScrollViewReader{ scrollReader in
                        LazyVGrid(columns: columns, spacing: 8) {
                            ForEach(viewModel.menuViewState.menuItems.indices){  i in
                                Section(
                                    header: LargeHeaderText(
                                        text:viewModel.menuViewState.menuItems[i].categorySectionItem.name
                                    )
                                    .id(viewModel.menuViewState.menuItems[i].categorySectionItem.id)
                                    .padding(.top, 16)
                                    ){
                                        ForEach(viewModel.menuViewState.menuItems[i].categorySectionItem.menuProdctItems){ menuProductItem in
                                            MenuItemView(
                                                menuProductItem: menuProductItem,
                                                isRootActive : $isRootActive,
                                                selection : $selection,
                                                showOrderCreated : $showOrderCreated,
                                                action: {
                                                    viewModel.addCartProductToCart(menuProductUuid: menuProductItem.productUuid)
                                                })
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
                        }
                        .padding(.horizontal, 16)
                        .onChange(of: viewModel.menuViewState, perform: { menuState in
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
        .background(AppColor.background)
        .navigationBarTitle("")
        .hiddenNavigationBarStyle()
        .preferredColorScheme(.light)
    }
}
