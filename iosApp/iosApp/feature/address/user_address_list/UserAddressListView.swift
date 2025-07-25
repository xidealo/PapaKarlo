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

    @State var userAddressViewState = UserAddressListState(
        userAddressList: [],
        eventList: [],
        state: UserAddressListState.State.loading
    )

    var title: LocalizedStringKey = "title_my_addresses"

    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    @State var show: Bool = false

    @State var isClickable: Bool

    @State var listener: Closeable?

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
            case UserAddressListState.State.loading: LoadingView()
            case UserAddressListState.State.empty: EmptyAddressListView(show: show)
            case UserAddressListState.State.success: SuccessAddressListView(
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
            listener = viewModel.addressListState.watch { addressListVM in
                if let notNullAddressListVM = addressListVM {
                    userAddressViewState = notNullAddressListVM
                }
            }
            viewModel.update()
        }
        .onDisappear {
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
    @State var userAddressListState: UserAddressListState
    @State var listener: Closeable?

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
        }.onAppear {
            viewModel.addressListState.watch { addressListVM in

                if let notNullAddressListVM = addressListVM {
                    userAddressListState = notNullAddressListVM
                }

                // work with actions
                for event in userAddressListState.eventList {
                    switch event {
                    case is UserAddressListStateEventGoBack:
                        closedCallback()
                        self.mode.wrappedValue.dismiss()
                    default:
                        print("def")
                    }
                }

                if !userAddressListState.eventList.isEmpty {
                    viewModel.consumeEventList(eventList: userAddressListState.eventList)
                }
            }
        }
        .onDisappear {
            listener?.close()
            listener = nil
        }.overlay(
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
