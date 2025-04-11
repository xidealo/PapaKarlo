//
//  LoadingView.swift
//  PapaKarloSwift
//
//  Created by Valentin on 07.05.2022.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        ZStack {
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: AppColor.primary))
                .scaleEffect(1.5)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(AppColor.background2)
        .hiddenNavigationBarStyle()
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
