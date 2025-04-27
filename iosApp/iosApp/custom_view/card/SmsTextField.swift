//
//  SmsTextField.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 20.03.2022.
//

import SwiftUI

struct SmsTextField: View {
    let count: Int

    var body: some View {
        HStack(spacing: 0) {
            ForEach(0 ... count, id: \.self) { _ in
                CodeView()
            }
        }
    }
}

struct SmsTextField_Previews: PreviewProvider {
    static var previews: some View {
        SmsTextField(count: 5)
    }
}
