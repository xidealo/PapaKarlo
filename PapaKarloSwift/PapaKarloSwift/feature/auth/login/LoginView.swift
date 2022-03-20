//
//  LoginView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 18.03.2022.
//

import SwiftUI

struct LoginView: View {
    
    @State var phone:String = ""
    
    var body: some View {
        
        VStack{
            
            Spacer()
 
            Image("LoginLogo").resizable().frame(width: 152, height: 120)
            Text(Strings.MSG_LOGIN_ENTER_PHONE).multilineTextAlignment(.center)
            
            TextField(Strings.HINT_LOGIN_PHONE, text:$phone)
                .padding().overlay(
                    RoundedRectangle(cornerRadius: Diems.MEDIUM_RADIUS)
                        .stroke(Color("surfaceVariant"), lineWidth: 2)
                )

            
            Spacer()
            NavigationLink(
                destination:ConfirmView()
            ){
                Text(Strings.ACTION_LOGIN_LOGIN)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .foregroundColor(Color("surface"))
                    .background(Color("primary"))
                    .cornerRadius(Diems.MEDIUM_RADIUS)
                    .font(.system(size: Diems.MEDIUM_TEXT_SIZE, weight: .medium, design: .default).smallCaps())
            }
        }.padding(Diems.MEDIUM_PADDING)
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
