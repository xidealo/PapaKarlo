//
//  WarningCard.swift
//  iosApp
//
//  Created by Марк Шавловский on 07.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct WarningCard: View {
    
    let title: String
    let icon: String
    let cardColor: Color
    
    var body: some View {
        ZStack{
            HStack(spacing:0){
                Image(
                    systemName: icon
                )
                .resizable()
                .renderingMode(.template)
                .frame(width: 24, height: 24)
                .foregroundColor(AppColor.onStatus)
                
                Text(title)
                    .titleMedium(weight: .bold)
                    .padding(.leading, 8)
                    .foregroundColor(AppColor.onStatus)
            }
        }.background(cardColor)
            .cornerRadius(16)
    }
}
