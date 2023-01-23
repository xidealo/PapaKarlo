//
//  UserAddressListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import SwiftUI
import shared

struct UserAddressListView: View {
    
    var viewModel = UserAddressListViewModel(
        getUserAddressList: iosComponent.provideGetUserAddressListUseCase(),
        addressInteractor: iosComponent.provideIAddressInteractor()
    )
    
    @State var userAddressViewState = UserAddressListState(
        userAddressList: [],
        eventList: [],
        state: UserAddressListState.State.loading
    )
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>
    
    @State var show:Bool = false
    
    @State var isClickable:Bool
    
    @State var listener: Closeable? = nil

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: Strings.TITLE_MY_ADDRESSES,
                back: {
                    self.mode.wrappedValue.dismiss()
                }
            )
            switch(userAddressViewState.state){
            case UserAddressListState.State.loading: LoadingView()
            case UserAddressListState.State.empty: EmptyAddressListView(show: show)
            case UserAddressListState.State.success : SuccessAddressListView(
                addressItemList: userAddressViewState.userAddressList.map({ userAddress in
                    AddressItem(id: userAddress.uuid, address: userAddress.getAddress(), isClickable: isClickable)
                }),
                show: show,
                viewModel: viewModel,
                userAddressListState: userAddressViewState
            )
            default : EmptyView()
            }
        }
        .background(Color("background"))
        .hiddenNavigationBarStyle()
        .onAppear(){
           listener = viewModel.addressListState.watch { addressListVM in
                if(addressListVM != nil ){
                    userAddressViewState = addressListVM!
                }
                // work with actions
                //почему-то тут не хочет слушать экшены (уточнить у ребят)
//                print("eventsS \(userAddressViewState.state)")
//                userAddressViewState.eventList.forEach { event in
//                    switch(event){
//                    case is UserAddressListStateEventGoBack :  self.mode.wrappedValue.dismiss()
//                    default:
//                        print("def")
//                    }
//                }
//
//                if !userAddressViewState.eventList.isEmpty{
//                    viewModel.consumeEventList(eventList: userAddressViewState.eventList)
//                }
                
            }
            viewModel.update()
        }
        .onDisappear(){
            listener?.close()
            listener = nil
        }
    }
}

struct SuccessAddressListView: View {
    
    let addressItemList: [AddressItem]
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var show:Bool
    let viewModel : UserAddressListViewModel
    @State var userAddressListState : UserAddressListState
    @State var listener: Closeable? = nil

    var body: some View {
        VStack(spacing:0){
            ScrollView {
                LazyVStack(spacing:0){
                    ForEach(addressItemList){ address in
                        if(address.isClickable){
                            Button(action: {
                                viewModel.onUserAddressChanged(userAddressUuid: address.id)
                            }) {
                                AddressItemView(addressItem: address)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                            }
                        }else{
                            AddressItemView(addressItem: address)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.top, Diems.SMALL_PADDING)

                        }
                    }
                }.padding(.vertical, Diems.SMALL_PADDING)
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
        }.onAppear(){
            viewModel.addressListState.watch { addressListVM in
                if(addressListVM != nil ){
                    userAddressListState = addressListVM!
                }
                // work with actions
                print("eventsS \(userAddressListState.state)")
                userAddressListState.eventList.forEach { event in
                    switch(event){
                    case is UserAddressListStateEventGoBack : self.mode.wrappedValue.dismiss()
                    default:
                        print("def")
                    }
                }

                if !userAddressListState.eventList.isEmpty{
                    viewModel.consumeEventList(eventList: userAddressListState.eventList)
                }
            }
        }
        .onDisappear(){
            listener?.close()
            listener = nil
        }.overlay(overlayView: ToastView(
            toast: Toast(title: "Адрес добавлен \(userAddressListState.userAddressList.last?.getAddress() ?? "")"),
            show: $show, backgroundColor:Color("primary"),
            foregaroundColor: Color("onPrimary")), show: $show)
    }
}

struct EmptyAddressListView: View {
    @State var show:Bool

    var body: some View {
        VStack(spacing:0){
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
