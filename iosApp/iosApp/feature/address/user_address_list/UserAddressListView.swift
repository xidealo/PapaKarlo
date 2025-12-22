//
//  UserAddressListView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 14.03.2022.
//

import shared
import SwiftUI

struct UserAddressListView: View {
    var viewModel = UserAddressListViewModel(
        getSelectableUserAddressListUseCase: iosComponent.provideGetSelectableUserAddressListUseCase(),
        saveSelectedUserAddressUseCase: iosComponent.provideSaveSelectedUserAddressUseCase()
    )

    @State var userAddressViewState = UserAddressListDataStateDataState(
        userAddressList: [],
        state: UserAddressListDataStateDataState.State.loading
    )

    var title: LocalizedStringKey = "title_my_addresses"

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var show: Bool = false

    @State var isClickable: Bool

    @State var listener: Closeable?
    @State var eventListener: Closeable?

    var closedCallback: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                title: title,
                back: {
                    closedCallback()
                    self.mode.wrappedValue.dismiss()
                }
            )
            switch userAddressViewState.state {
            case UserAddressListDataStateDataState.State.loading: LoadingView()
            case UserAddressListDataStateDataState.State.empty: EmptyAddressListView(show: show)
            case UserAddressListDataStateDataState.State.success: SuccessAddressListView(
                    addressItemList: userAddressViewState.userAddressList.map { userAddress in
                        AddressItem(
                            id: userAddress.address.uuid,
                            address: userAddress.getAddress(),
                            isClickable: isClickable,
                            isSelected: userAddress.isSelected,
                            isEnabled: true
                        )
                    },
                    show: show,
                    viewModel: viewModel,
                    userAddressListState: userAddressViewState,
                    closedCallback: closedCallback
                )
            default: EmptyView()
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear {
            viewModel.onAction(action: UserAddressListDataStateActionInit())

            listener = viewModel.dataState.watch { addressListVM in
                if let notNullAddressListVM = addressListVM {
                    userAddressViewState = notNullAddressListVM
                }
            }
            
            eventListener = viewModel.events.watch { _events in
        
                if let events = _events {
                    let userAddressListEvents = events as? [UserAddressListDataStateEvent] ?? []

                    for event in userAddressListEvents {
                        print(event)
                        switch event {
                        case is UserAddressListDataStateEventGoBackEvent:
                            closedCallback()
                            self.mode.wrappedValue.dismiss()
                        default:
                            print("def")
                        }
                    }
                    
                    if !userAddressListEvents.isEmpty {
                        viewModel.consumeEvents(events: userAddressListEvents)
                    }
                }
            }
        }
        .onDisappear {
            eventListener?.close()
            eventListener = nil
            listener?.close()
            listener = nil
        }
    }
}

struct SuccessAddressListView: View {
    let addressItemList: [AddressItem]

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var show: Bool
    let viewModel: UserAddressListViewModel
    @State var userAddressListState: UserAddressListDataStateDataState
    @State var eventListener: Closeable?

    var closedCallback: () -> Void

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                LazyVStack(spacing: 0) {
                    ForEach(addressItemList) { address in
                        if address.isClickable {
                            Button(action: {
                                viewModel.onUserAddressChanged(userAddressUuid: address.id)
                            }) {
                                AddressItemView(addressItem: address)
                                    .padding(.horizontal, Diems.MEDIUM_PADDING)
                                    .padding(.top, Diems.SMALL_PADDING)
                            }
                        } else {
                            AddressItemView(addressItem: address)
                                .padding(.horizontal, Diems.MEDIUM_PADDING)
                                .padding(.top, Diems.SMALL_PADDING)
                        }
                    }
                }.padding(.vertical, Diems.SMALL_PADDING)
                    .padding(.bottom, Diems.BUTTON_HEIGHT * 2)
                    .padding(.bottom, Diems.MEDIUM_PADDING)
            }

            NavigationLink(
                destination: CreateAddressView(show: $show)
            ) {
                ButtonText(text: Strings.ACTION_ADDRESS_LIST_ADD)
            }.padding(Diems.MEDIUM_PADDING)
        }
       .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Адрес добавлен \(userAddressListState.userAddressList.last?.getAddress() ?? "")"),
                show: $show, backgroundColor: AppColor.primary,
                foregroundColor: AppColor.onPrimary
            ), show: $show
        )
    }
}

struct EmptyAddressListView: View {
    @State var show: Bool

    var body: some View {
        VStack(spacing: 0) {
            Spacer()

            EmptyWithIconView(
                imageName: "AddressIcon",
                title: "emptyAddressListTitleProfile",
                secondText: "emptyAddressListSecondProfile"
            )

            Spacer()

            NavigationLink(
                destination: CreateAddressView(show: $show)
            ) {
                ButtonText(text: Strings.ACTION_ADDRESS_LIST_ADD)
            }.padding(Diems.MEDIUM_PADDING)
        }
    }
}
