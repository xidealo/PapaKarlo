//
//  AppTypography.swift
//  iosApp
//
//  Created by Марк Шавловский on 09.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

extension Text {
    func titleSmall(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default))
            .lineSpacing(20)
    }
    func titleMedium(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 16, weight: weight, design: .default))
            .lineSpacing(24)
    }
    func labelSmall(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 11, weight: weight, design: .default))
            .lineSpacing(16)
    }
    func labelMedium(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 12, weight: weight, design: .default))
            .lineSpacing(16)
    }
    func labelLarge(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default)).lineSpacing(20)
    }
    func bodySmall(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 12, weight: weight, design: .default))
            .lineSpacing(16)
    }
    func bodyMedium(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default))
            .lineSpacing(20)
    }
    func bodyLarge(weight: Font.Weight = .regular) -> some View {
        return self
            .font(Font.system(size: 16, weight: weight, design: .default))
            .lineSpacing(24)
    }

}
