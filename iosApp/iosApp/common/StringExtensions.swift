//
//  StringExtensions.swift
//  iosApp
//
//  Created by Марк Шавловский on 25.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension String {
    func trimingLeadingSpaces(using characterSet: CharacterSet = .whitespacesAndNewlines) -> String {
        guard let index = firstIndex(where: { !CharacterSet(charactersIn: String($0)).isSubset(of: characterSet) }) else {
            return self
        }

        return String(self[index...])
    }
    
    func trimingTrailingSpaces(using characterSet: CharacterSet = .whitespacesAndNewlines) -> String {
            guard let index = lastIndex(where: { !CharacterSet(charactersIn: String($0)).isSubset(of: characterSet) }) else {
                return self
            }

            return String(self[...index])
        }
}

