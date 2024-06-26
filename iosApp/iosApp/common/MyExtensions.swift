//
//  StringExtensions.swift
//  iosApp
//
//  Created by Марк Шавловский on 25.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

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
    
    func getPaymentMethod() -> LocalizedStringKey {
        switch(self){
        case PaymentMethodName.cash.name: return "msg_payment_cash"
        case PaymentMethodName.card.name: return "msg_payment_card"
        case PaymentMethodName.cardNumber.name: return "msg_payment_card_number"
        case PaymentMethodName.phoneNumber.name: return "msg_payment_phone_number"
        default : return ""
        }
    }
    
    func load() -> UIImage {
        do {
            guard let url = URL(string: self) else {
                return UIImage()
            }
            let data:Data = try Data(contentsOf: url)
            return UIImage(data: data) ?? UIImage()
        }catch {
            return UIImage()
        }
    }
}

extension UserAddress {
    func getAddress() -> String {
        
        var address = self.street
        address += ", д. " + (self.house)
        
        if let flat = self.flat{
            if(flat != ""){
                address += ", кв. " + flat
            }
        }
        
        if let entrance = self.entrance{
            if(self.entrance != ""){
                address += ", подъезд " + entrance
            }
        }
        
        if let floor = self.floor{
            if(self.floor != ""){
                address += ", этаж " + floor
            }
        }
        
        if let comment = self.comment{
            if(self.comment != ""){
                address += ", \(comment)"
            }
        }
        
        return address
    }
}

extension SelectableUserAddress {
    func getAddress() -> String {
        
        var address = self.address.street
        address += ", д. " + (self.address.house)
        
        if(self.address.flat != nil && self.address.flat != ""){
            address += ", кв. " + (self.address.flat ?? "")
        }
        
        if(self.address.entrance != nil && self.address.entrance != ""){
            address += ", подъезд " + (self.address.entrance ?? "")
        }
        
        if(self.address.floor != nil && self.address.floor != ""){
            address += ", этаж. " + (self.address.floor ?? "")
        }
        
        if(self.address.comment != nil && self.address.comment != ""){
            address += ", \(self.address.comment ?? "")"
        }
        
        return address
    }
}

extension OrderAddress {
    func getAddress() -> String {
        
        if(street == nil){
            return self.description_ ?? ""
        }
        
        var address = self.street ?? ""
        address += ", д. " + (self.house ?? "")
        
        if(self.flat != nil && self.flat != ""){
            address += ", кв. " + (self.flat ?? "")
        }
        
        if(self.entrance != nil && self.entrance != ""){
            address += ", подъезд " + (self.entrance ?? "")
        }
        
        if(self.floor != nil && self.floor != ""){
            address += ", этаж. " + (self.floor ?? "")
        }
        
        if(self.comment != nil && self.comment != ""){
            address += ", \(self.comment ?? "")"
        }
        
        return address
    }
}

extension PaymentMethodName{
    func getPaymentMethod() -> LocalizedStringKey {
        switch(self){
        case PaymentMethodName.cash: return "msg_payment_cash"
        case PaymentMethodName.card: return "msg_payment_card"
        case PaymentMethodName.cardNumber: return "msg_payment_card_number"
        case PaymentMethodName.phoneNumber: return "msg_payment_phone_number"
        default : return ""
        }
    }
}
