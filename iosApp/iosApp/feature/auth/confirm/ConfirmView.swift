//
//  ConfirmView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI

struct ConfirmView: View {
    
    @State private var code:String = ""
    
    @ObservedObject private var viewModel : ConfirmViewModel

    init(auth:AuthManager){
        viewModel = ConfirmViewModel(auth: auth)
    }
    
    var body: some View {
        if viewModel.confirmViewState.isLoading{
            LoadingView()
        }else{
            if viewModel.confirmViewState.isGoToProfile{
                NavigationLink(
                    destination:ContainerView(selection: 2),
                    isActive: .constant(true)
                ){
                    Text("")
                }
            }else{
                ConfirmViewSuccessView(code: $code, viewModel: viewModel)
            }
        }
    }
}

struct ConfirmViewSuccessView: View {
    @Binding var code:String
    @ObservedObject var viewModel : ConfirmViewModel
    
    @State private var timeRemaining = 60
    @State private var isEnabled = false
    
    let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
    
    var body: some View {
        
        VStack{
            ToolbarView(title: "", cost: "", count: "",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            Spacer()
            Text(Strings.MSG_CONFIRM_ENTER_CODE).multilineTextAlignment(.center)
    
            //SmsTextField(count: 6)
            
            EditTextView(hint: Strings.HINT_CONFIRM_CODE, text:$code)

            Spacer()
            
            Button {
                viewModel.checkCode(code: code)
            } label: {
                Text(Strings.ACTION_LOGIN_LOGIN)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }
            
            if(isEnabled){
                Button(
                    action: {
                        isEnabled = false
                        timeRemaining = 60
                    }
                ){
                    Text(Strings.ACTION_CONFIRM_GET_CODE)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .foregroundColor(Color("surface"))
                        .background(Color("primary"))
                        .cornerRadius(Diems.MEDIUM_RADIUS)
                        .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
                }
            }else {
                PlaceholderText(text: "Получить код повторно можно через \(timeRemaining) секунд").multilineTextAlignment(.center)
            }
        }.padding(Diems.MEDIUM_PADDING).onReceive(timer){ time in
            
            if timeRemaining > 0{
                timeRemaining -= 1
            }
            
            if(timeRemaining == 0){
                isEnabled = true
            }

        }.navigationBarHidden(true)
    }
}

struct ConfirmView_Previews: PreviewProvider {
    static var previews: some View {
        ConfirmView(auth: AuthManager())
    }
}
