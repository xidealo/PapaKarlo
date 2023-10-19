//
//  DiscountCard.swift
//  iosApp
//
//  Created by Марк Шавловский on 03.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct DiscountCard: View {
    
    let text:String
    
    var body: some View {
        Text(text)
            .foregroundColor(AppColor.onStatus)
            .bodyMedium()
            .padding(.horizontal, 4)
            .background(AppColor.positive)
            .cornerRadius(4)
    }
}


