//
//  DateUtil.swift
//  iosApp
//
//  Created by Марк Шавловский on 10.09.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class DateUtil {
    
    func getDateTimeString(dateTime: DateTime) -> String {
        var monthName = ""
        
        switch(dateTime.date.monthNumber){
        case 1: monthName = "января"
        case 2: monthName = "февраля"
        case 3: monthName = "марта"
        case 4: monthName = "апреля"
        case 5: monthName = "мая"
        case 6: monthName = "июня"
        case 7: monthName = "июля"
        case 8: monthName = "августа"
        case 9: monthName = "сентября"
        case 10: monthName = "октября"
        case 11: monthName = "ноября"
        case 12: monthName = "декабря"

        default : monthName = ""
            
        }
   
        return "\(dateTime.date.datOfMonth) \(monthName) \(getTimeString(time: dateTime.time))"
    }
    
    func getTimeString(time: Time) -> String {
        return "\(addFirstZero(number: Int(time.hourOfDay))):\(addFirstZero(number : Int(time.minuteOfHour)))"
       }
    
    func addFirstZero(number: Int) -> String {
            if (number < 10) {
                return "0\(number)"
           } else {
               return "\(number)"
           }
       }
}
