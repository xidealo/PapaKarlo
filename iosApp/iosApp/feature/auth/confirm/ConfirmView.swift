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

    @State var viewModel = ConfirmViewModel(
        formatPhoneNumber: iosComponent.provideFormatPhoneNumberUseCase(),
        checkCode: iosComponent.provideCheckCodeUseCase(),
        resendCode: iosComponent.provideResendCodeUseCase()
    )

    //State
    @State var phoneNumber:String = ""
    @State var resendSeconds:Int = 60
    @State var isLoading:Bool = false
    // ---


    //Events
    @State var showTooManyRequestsError:Bool = false
    @State var showNoAttemptsError:Bool = false
    @State var showInvalidCodeError:Bool = false
    @State var showAuthSessionTimeoutError:Bool = false
    @State var showSomethingWentWrongError:Bool = false

    @Binding var rootIsActive:Bool
    @Binding var isGoToCreateOrder:Bool
    
    @State var showLoginError:Bool = false

    @State var stateListener: Closeable? = nil
    @State var eventsListener: Closeable? = nil

    init(phone:String, rootIsActive: Binding<Bool>, isGoToCreateOrder: Binding<Bool>){
        self._rootIsActive = rootIsActive
        self._isGoToCreateOrder = isGoToCreateOrder
        self.viewModel.handleAction(action: ConfirmActionInit(phoneNumber: phone, direction: SuccessLoginDirection.backToProfile))
        }
    
    var body: some View {
        VStack(spacing:0){
            if(isLoading){
                LoadingView()
            }else{
                ConfirmViewSuccessView(
                    code: $code,
                    phone: $phoneNumber,
                    resendSeconds: $resendSeconds,
                    action: viewModel.handleAction
                )
            }
        }
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Превышен лимит на отправку сообщений"),
                show: $showTooManyRequestsError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showTooManyRequestsError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Превышено количество попыток ввода кода"),
                show: $showNoAttemptsError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showNoAttemptsError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Неверный код"),
                show: $showInvalidCodeError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showInvalidCodeError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Истекло время на ввод кода"),
                show: $showAuthSessionTimeoutError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showAuthSessionTimeoutError
        )
        .overlay(
            overlayView: ToastView(
                toast: Toast(title: "Что-то пошло не так"),
                show: $showSomethingWentWrongError,
                backgroundColor: AppColor.error,
                foregaroundColor: AppColor.onError
            ), show: $showSomethingWentWrongError
        )
        .onAppear(){
            subscribe()
            eventsSubscribe()
        }
        .onDisappear(){
            unsubscribe()
        }
        .hiddenNavigationBarStyle()
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
                    case is ConfirmEventShowNoAttemptsError : showNoAttemptsError = true
                    case is ConfirmEventShowInvalidCodeError : showInvalidCodeError = true
                    case is ConfirmEventShowAuthSessionTimeoutError : showAuthSessionTimeoutError = true
                    case is ConfirmEventShowSomethingWentWrongError : showSomethingWentWrongError = true
                    case is ConfirmEventNavigateBackToProfile : rootIsActive = false
                        isGoToCreateOrder = true
                    case is ConfirmEventNavigateToCreateOrder : rootIsActive = false
                        isGoToCreateOrder = true
                    case is ConfirmEventNavigateBack : self.mode.wrappedValue.dismiss()
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
    
    @Binding var phone: String
    @Binding var resendSeconds: Int

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
                        action(ConfirmActionResendCode())
                    }
                ){
                    if(resendSeconds == 0){
                        ButtonText(text: Strings.ACTION_CONFIRM_GET_CODE)
                    }
                    else{
                        ButtonText(
                            text: "Запросить код повторно \(resendSeconds) сек.",
                            background: AppColor.disabled,
                            foregroundColor: AppColor.onDisabled
                        )
                    }
                    
                }.disabled(resendSeconds != 0)
            }
            .padding(Diems.MEDIUM_PADDING)
        }
        .background(AppColor.surface)
    }
}
