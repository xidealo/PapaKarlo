//
//  ConfirmView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI

struct ConfirmView: View {
    
    @State private var timeRemaining = 60
    @State private var isEnabled = false

    let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
    
    var body: some View {
        
        VStack{
            ToolbarView(title: "", cost: "220 R", count: "2",  isShowBackArrow: true, isCartVisible: false, isLogoutVisible: false)
            
            Spacer()
            Text(Strings.MSG_CONFIRM_ENTER_CODE).multilineTextAlignment(.center)
    
            SmsTextField(count: 6)
            
            Spacer()
            
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
        ConfirmView()
    }
}
