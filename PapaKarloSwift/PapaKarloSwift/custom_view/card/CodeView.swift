//
//  CodeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import SwiftUI
import Combine

struct CodeView: View {
    
    @State var code:String
    
    private let textLimit = 1 //Your limit

    var body: some View {
        VStack{
            TextField("", text: $code)
               .foregroundColor(Color("onSurface"))
                .multilineTextAlignment(.center)
                .keyboardType(.numberPad)
                .onReceive(Just(code)) { _ in limitText(textLimit) }

                
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
    
    func limitText(_ upper: Int) {
            if code.count > upper {
                code = String(code.prefix(upper))
            }
        }
}

struct CodeView_Previews: PreviewProvider {
    static var previews: some View {
        CodeView(code:"7")
    }
}
