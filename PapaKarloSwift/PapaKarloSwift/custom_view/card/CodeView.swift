//
//  CodeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI

struct CodeView: View {
    
    @State var code:String
    
    var body: some View {
        VStack{
            TextField("", text: $code)
                .foregroundColor(Color("onSurface")).multilineTextAlignment(.center)
            if(code != ""){
                Capsule()
                    .fill(Color("primary"))
                    .frame(height:2)
            }else {
                Capsule()
                    .fill(Color("onSurface"))
                    .frame(height:2)
            }
        }
    }
}

struct CodeView_Previews: PreviewProvider {
    static var previews: some View {
        CodeView(code:"7")
    }
}
