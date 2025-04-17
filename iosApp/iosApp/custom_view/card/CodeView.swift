//
//  CodeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 24.03.2022.
//

import Combine
import SwiftUI

struct CodeView: View {
    @State var code: String = ""

    private let textLimit = 1 // Your limit

    var body: some View {
        VStack {
            TextField("", text: $code)
                .foregroundColor(AppColor.onSurface)
                .multilineTextAlignment(.center)
                .keyboardType(.numberPad)
                .onReceive(Just(code)) { _ in limitText(textLimit) }

            if code != "" {
                Capsule()
                    .fill(AppColor.primary)
                    .frame(height: 2)
            } else {
                Capsule()
                    .fill(AppColor.onSurface)
                    .frame(height: 2)
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
        CodeView(code: "7")
    }
}
