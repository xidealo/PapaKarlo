//
//  UserAddressListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI

struct UserAddressListView: View {
    
    @ObservedObject private var viewModel : UserAddressListViewModel
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var show:Bool = false

    init(isClickable:Bool){
        viewModel = UserAddressListViewModel(isClickable: isClickable)
    }
    
    var body: some View {
        VStack{
            ToolbarView(title: Strings.TITLE_MY_ADDRESSES, cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            switch(viewModel.userAddressViewState.userAddressState){
            case UserAddressState.loading: LoadingView()
            case UserAddressState.empty: EmptyAddressListView(show: show)
            case UserAddressState.success : SuccessAddressListView(viewModel: viewModel, show: show)
            case UserAddressState.goBack : EmptyView()
            }
            
        }.onReceive(viewModel.$userAddressViewState, perform: { userAddressViewState in
            if(userAddressViewState.userAddressState == UserAddressState.goBack){
                self.mode.wrappedValue.dismiss()
            }
        })
        .background(Color("background"))
        .navigationBarHidden(true)
        
    }
}

struct UserAddressListView_Previews: PreviewProvider {
    static var previews: some View {
        UserAddressListView(isClickable: false)
    }
}

struct SuccessAddressListView: View {
    @ObservedObject var viewModel:UserAddressListViewModel

    @State var show:Bool

    var body: some View {
        VStack(spacing:0){
            ScrollView {
                LazyVStack(spacing:0){
                    ForEach(viewModel.userAddressViewState.addressItemList){ address in
                        if(address.isClickable){
                            Button(action: {
                                viewModel.selectAddress(uuid: address.id)
                            }) {
                                AddressItemView(addressItem: address)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                                    .padding(.bottom, Diems.SMALL_PADDING)
                            }
                        }else{
                            AddressItemView(addressItem: address)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.top, Diems.SMALL_PADDING)
                                .padding(.bottom, Diems.SMALL_PADDING)

                        }
                    }
                }
            }
            
            NavigationLink(
                destination:CreateAddressView(show: $show)
            ){
                Text(Strings.ACTION_ADDRESS_LIST_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }.overlay(overlayView: ToastView(
            toast: Toast(title: "Адрес добавлен \(viewModel.userAddressViewState.addressItemList.last?.address ?? "")"),
            show: $show, backgroundColor:Color("primary"),
            foregaroundColor: Color("onPrimary")), show: $show)
        
    }
}

struct EmptyAddressListView: View {
    @State var show:Bool

    var body: some View {
        VStack{
            Spacer()
            
            Image("EmptyPage")
            
            Text(Strings.MSG_ADDRESS_LIST_EMPTY_ADDRESSES).multilineTextAlignment(.center)
            Spacer()
            
            NavigationLink(
                destination:CreateAddressView(show: $show)
            ){
                Text(Strings.ACTION_ADDRESS_LIST_ADD).frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}
