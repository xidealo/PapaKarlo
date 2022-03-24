//
//  SmsTextField.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 20.03.2022.
//

import SwiftUI

struct SmsTextField: View {
    let codes:[String]
    
    var body: some View {
    
        HStack{
            ForEach(self.codes, id: \.self){ code in
                CodeView(code: code)
            }
        }

    }
}



struct SmsTextField_Previews: PreviewProvider {
    static var previews: some View {
        SmsTextField(codes: ["1", "2", "7", "", "", ""])
    }
}
