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
    func titleSmall(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default))
    }
    func titleMedium(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 16, weight: weight, design: .default))
    }
    func labelSmall(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 11, weight: weight, design: .default))
    }
    func labelMedium(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 12, weight: weight, design: .default))
    }
    func labelLarge(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default))
    }
    func bodySmall(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 12, weight: weight, design: .default))
    }
    func bodyMedium(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 14, weight: weight, design: .default))
    }
    func bodyLarge(weight: Font.Weight = .regular) -> Text {
        return self
            .font(Font.system(size: 16, weight: weight, design: .default))
    }

}
