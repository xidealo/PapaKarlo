//
//  UpdateView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 17.03.2022.
//

import SwiftUI
import shared

struct UpdateView: View, SharedLifecycleWithState {
    
    @State var viewModel: UpdateViewModel = UpdateViewModel(
        getLinkUseCase: iosComponent.provideGetLinkUseCase()
    )
    
    @State var updateViewState = UpdateViewState(
        state: UpdateState.loading
    )
    
    @State var listener: Closeable?
    
    @State var eventsListener: Closeable?
    
    var body: some View {
        VStack(spacing : 0){
            switch updateViewState.state {
            case .loading:
                LoadingView()
            case .error:
                ErrorView(
                    mainText: "Ошибка",
                    extratext: "Что-то пошло не так",
                    action: {
                        viewModel.onAction(action: UpdateStateActionInit(linkType: LinkType.appStore))
                    }
                )
            case .success(let link):
                Spacer()
                
                DefaultImage(imageName: "NewVersion")
                
                Text(Strings.MSG_UPDATE_GO_TO)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 16)
                
                Spacer()
                
                Button(
                    action: {
                        viewModel.onAction(action: UpdateStateActionUpdateClick(linkValue: link?.linkValue ?? ""))
                    },
                    label: {
                        ButtonText(
                            text: Strings.ACTION_UPDATE_UPDATE,
                            background: AppColor.primary,
                            foregroundColor: AppColor.onPrimary
                        )
                    }
                )
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.vertical, Diems.MEDIUM_PADDING)
            }
        }
        .background(AppColor.background)
        .hiddenNavigationBarStyle()
        .onAppear(perform: {
            viewModel.onAction(
                action: UpdateStateActionInit(linkType: LinkType.appStore)
            )
            subscribe()
            eventsSubscribe()
        })
        .onDisappear(){
            unsubscribe()
        }
    }
    
    func subscribe() {
        viewModel.dataState.watch { updateVM in
            if let updateStateVM =  updateVM {
                switch updateStateVM.state {
                case UpdateStateDataState.State.loading : updateViewState = UpdateViewState(state: UpdateState.loading)
                case UpdateStateDataState.State.error : updateViewState = UpdateViewState(state: UpdateState.error)
                case UpdateStateDataState.State.success : updateViewState =  UpdateViewState(
                    state: UpdateState.success(updateStateVM.link)
                )
                default:
                    updateViewState =  UpdateViewState(state: UpdateState.error)
                }
            }
        }
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let updateEvents = events as? [UpdateStateEvent] ?? []
                
                updateEvents.forEach { event in
                    switch(event) {
                    case is UpdateStateEventNavigateToUpdateEvent :
                        if let url = URL(string: (event as? UpdateStateEventNavigateToUpdateEvent)?.linkValue ?? "") {
                            if UIApplication.shared.canOpenURL(url) {
                                UIApplication.shared.open(url, options: [:], completionHandler: nil)
                            } else {
                                print("Не удается открыть URL")
                            }
                        }
                    default:
                        print("def")
                    }
                }
                
                if !updateEvents.isEmpty {
                    viewModel.consumeEvents(events: updateEvents)
                }
            }
        })
    }
    func unsubscribe(){
        listener?.close()
        listener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct UpdateView_Previews: PreviewProvider {
    static var previews: some View {
        UpdateView()
    }
}
