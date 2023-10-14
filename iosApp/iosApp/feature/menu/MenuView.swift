//
//  MenuView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 23.02.2022.
//

import SwiftUI
import shared

let DISCOUNT_ID = "discount_id"

//Необходимо, чтобы сбрасывать значение с scrollToPosition, чтобы тригерился паблишер
let NO_POSITION = "no_pos"

struct MenuView: View {
    
    @StateObject private var viewModel = MenuViewModel()
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
                            ForEach(Array(viewModel.menuViewState.categoryItemModels.enumerated()), id: \.offset){ index, categoryItemModel in
                                CategoryItemView(
                                    categoryItemModel: categoryItemModel,
                                    action: viewModel.seletTagWithScroll
                                )
                                .padding(.horizontal, Diems.HALF_SMALL_PADDING)
                                .padding(.leading, index == 0 ? 12 : 0)
                                .padding(.trailing, index == viewModel.menuViewState.categoryItemModels.count - 1 ? 12 : 0)
                                .id(categoryItemModel.id)
                            }
                        }
                        .onChange(of: viewModel.menuViewState, perform: { menuState in
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
                        if let discount = viewModel.menuViewState.discount {
                            DiscountView(discount: discount)
                                .id(DISCOUNT_ID)
                        }
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
                                            //print("onAppear \(i)")
                                            viewModel.checkAppear(index: i)
                                        }
                                        .onDisappear(){
                                            //print("onDisappear \(i)")
                                            viewModel.checkDisappear(index: i)
                                        }
                                    }
                                }
                            }
                        }
                        .padding(.bottom, 8)
                        .padding(.horizontal, 16)
                        .onChange(of: viewModel.menuViewState.scrollToPostion, perform: { scrollToPostion in
                            if(scrollToPostion != NO_POSITION){
                                withAnimation(.spring()){
                                    scrollReader.scrollTo(scrollToPostion, anchor: .top)
                                }
                                viewModel.dropScrollPostion()
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
    
    func DiscountView(discount:String) -> some View {
        VStack(spacing:0){
            HStack(spacing:0){
                IconImage(
                    width: 24,
                    height: 24,
                    imageName: "ic_discount"
                )
                .foregroundColor(AppColor.onStatus)
                Text("Скидка \(discount)%")
                    .titleMedium(weight: .bold)
                    .padding(.leading, 8)
                    .foregroundColor(AppColor.onStatus)
                Spacer()
            }
            Text("Успей сделать первый заказ со скидкой \(discount)%")
                .bodyLarge()
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.top, 8)
                .foregroundColor(AppColor.onStatus)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(AppColor.positive)
        .cornerRadius(Diems.LARGE_RADIUS)
        .padding(.horizontal, 16)
        .padding(.top, 16)
    }
}
