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
                .progressViewStyle(CircularProgressViewStyle(tint: .gray))
                .scaleEffect(2)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .hiddenNavigationBarStyle()
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
