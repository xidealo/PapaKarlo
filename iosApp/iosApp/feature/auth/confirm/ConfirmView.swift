//
//  ConfirmView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI
import Combine
import shared

struct ConfirmView: View {
    
    @State private var code:String = ""
    
    @Environment(\.presentationMode) var mode: Binding<PresentationMode>

    private let viewModel = ConfirmViewModel(
        formatPhoneNumber: iosComponent.provideFormatPhoneNumberUseCase(),
        checkCode: iosComponent.provideCheckCodeUseCase(),
        resendCode: iosComponent.provideResendCodeUseCase()
    )
    
    //State
    @State var phoneNumber:String = ""
    @State var resendSeconds:Int = 60
    @State var isLoading:Bool = false
    // ---
    
    @Binding var rootIsActive:Bool
    @Binding var isGoToCreateOrder:Bool
    
    @State var showLoginError:Bool = false

    @State var stateListener: Closeable? = nil
    @State var eventsListener: Closeable? = nil
    
    init(phone:String, rootIsActive: Binding<Bool>, isGoToCreateOrder: Binding<Bool>){
        viewModel.handleAction(action: ConfirmActionInit(phoneNumber: phone, direction: SuccessLoginDirection.backToProfile))
        self._rootIsActive = rootIsActive
        self._isGoToCreateOrder = isGoToCreateOrder
    }
    
    var body: some View {
        VStack(spacing:0){
            if(isLoading){
                LoadingView()
            }else{
                ConfirmViewSuccessView(code: $code, phone: phoneNumber, action: viewModel.handleAction)
            }
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Ошибка от сервера, попробуйте позже"),
                show: $showLoginError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showLoginError
        )
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }

    }
    
    func subscribe(){
        stateListener = viewModel.state.watch { confirmStateVM in
            if let confirmState = confirmStateVM{
                phoneNumber = confirmState.phoneNumber
                resendSeconds = Int(confirmState.resendSeconds)
                isLoading = confirmState.isLoading
            }
        }
    }
    
    func eventsSubscribe(){
        eventsListener = viewModel.events.watch(block: { _events in
            if let events = _events{
                let confirmEvents = events as? [ConfirmEvent] ?? []
                
                confirmEvents.forEach { event in
                    switch(event){
                    case is ConfirmEventShowTooManyRequestsError : self.mode.wrappedValue.dismiss()
                    case is ConfirmEventShowNoAttemptsError : print("add error")
                    case is ConfirmEventShowInvalidCodeError : print("add error")
                    case is ConfirmEventShowAuthSessionTimeoutError : print("add error")
                    case is ConfirmEventShowSomethingWentWrongError : print("add error")
                    case is ConfirmEventNavigateBackToProfile : rootIsActive = false
                        isGoToCreateOrder = true
                    case is ConfirmEventNavigateToCreateOrder : rootIsActive = false
                        isGoToCreateOrder = true
                    case is ConfirmEventNavigateBack : self.mode.wrappedValue.dismiss()
                    
//                    case ConfirmAction.back :
//                                  case ConfirmAction.showCodeError: showLoginError = true
//                                  case ConfirmAction.showLoginError : showLoginError = true
//
                    default:
                        print("not checked event")
                    }
                }
                
                if !confirmEvents.isEmpty {
                    viewModel.consumeEvents(events: confirmEvents)
                }
            }
        })
    }
    
    func unsubscribe(){
        stateListener?.close()
        stateListener = nil
        eventsListener?.close()
        eventsListener = nil
    }
}

struct ConfirmViewSuccessView: View {
    @Binding var code:String
    @State var show:Bool = false
    
    @State private var timeRemaining = 60
    @State private var isEnabled = false
    
    let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
    let phone:String
    
    let action: (ConfirmAction) -> Void

    var body: some View {
        VStack(spacing:0){
            ToolbarView(
                title: "",
                back: {
                    action(ConfirmActionBackClick())
                }
            )

            VStack(spacing:0){
                
                Spacer()

                Text(Strings.MSG_CONFIRM_ENTER_CODE + phone)
                    .bodyLarge()
                    .multilineTextAlignment(.center)
                    .foregroundColor(AppColor.onSurface)
                    .padding(.bottom, Diems.MEDIUM_PADDING)

                //SmsTextField(count: 6)
                
                EditTextView(
                    hint: Strings.HINT_CONFIRM_CODE,
                    text:$code,
                    limit: 6,
                    keyBoadrType: UIKeyboardType.numberPad,
                    hasError: .constant(false),
                    textChanged: { str in
                        
                    }
                )
                .onReceive(Just(code)) { _ in
                    if(code.count == 6){
                        action(ConfirmActionCheckCode(code: code))
                        code = ""
                    }
                }
                
                Spacer()
                
                Button(
                    action: {
                        isEnabled = false
                        timeRemaining = 60
                        action(ConfirmActionResendCode())
                    }
                ){
                    if(isEnabled){
                        ButtonText(text: Strings.ACTION_CONFIRM_GET_CODE)
                    }
                    else{
                        ButtonText(
                            text: "Запросить код повторно \(timeRemaining) сек.",
                            background: AppColor.disabled,
                            foregroundColor: AppColor.onDisabled
                        )
                    }
                    
                }.disabled(!isEnabled)
            }
            .padding(Diems.MEDIUM_PADDING)
        }
        .background(AppColor.surface)
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Неправильный код"),
                show: $show,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $show)
        .onReceive(timer){ time in
            if timeRemaining > 0{
                timeRemaining -= 1
            }
            
            if(timeRemaining == 0){
                isEnabled = true
            }
            print(isEnabled)
        }
        .hiddenNavigationBarStyle()
    }
}
