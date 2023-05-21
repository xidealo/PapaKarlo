//
//  CafeView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import SwiftUI
import shared

struct CafeItemView: View {

    var cafeItem: CafeItemUi

    var body: some View {
        VStack(spacing:0){
            Text(cafeItem.address)
                .bodyMedium()
                .foregroundColor(AppColor.onSurface)
                .padding(.horizontal,  Diems.MEDIUM_PADDING)
                .padding(.top, 12)
                .frame(maxWidth: .infinity, alignment: .leading)

            HStack(spacing:0){
                Text(cafeItem.workingHours)
                    .labelMedium()
                    .foregroundColor(AppColor.onSurfaceVariant)

                Text(getCafeStatusText(cafeStatus: cafeItem.cafeOpenState))
                    .labelMedium()
                    .foregroundColor(getCafeStatusColor(cafeStatus: cafeItem.cafeOpenState))
                    .padding(.leading, 4)
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.bottom, 12)
            .padding(.top, 8)
            .frame(maxWidth: .infinity, alignment: .leading)
            
        }.background(AppColor.surface)
            .cornerRadius(15)
    }
}


func getCafeStatusColor(cafeStatus: CafeItem.CafeOpenState) -> Color {
    switch cafeStatus {
    case is CafeItem.CafeOpenStateOpened:
        return AppColor.positive
    case is CafeItem.CafeOpenStateCloseSoon:
        return AppColor.warning
    case is CafeItem.CafeOpenStateClosed:
        return AppColor.negative
    default:
        return AppColor.warning
    }
}

func getCafeStatusText(cafeStatus: CafeItem.CafeOpenState) -> String {
    switch cafeStatus {
    case is CafeItem.CafeOpenStateOpened:
        return NSLocalizedString("msg_cafe_open", comment: "")
    case is CafeItem.CafeOpenStateCloseSoon :
        let minutes = Int((cafeStatus as? CafeItem.CafeOpenStateCloseSoon)?.time ?? 0)
        let minuteString = getMinuteString(
            closeIn: minutes
        )
        return NSLocalizedString("msg_cafe_close_soon", comment: "") + String(minutes) + " " + minuteString
    case is CafeItem.CafeOpenStateClosed:
        return NSLocalizedString("msg_cafe_closed", comment: "")
    default:
        return "CLose"
    }
    
}

private func getMinuteString(closeIn: Int) -> String {
    let minuteStringId: String
    switch closeIn {
    case 10...19:
        minuteStringId = "msg_cafe_minutes"
    case let x where x % 10 == 1:
        minuteStringId = "msg_cafe_minute"
    case 2...4:
        minuteStringId = "msg_cafe_minutes_234"
    default:
        minuteStringId = "msg_cafe_minutes"
    }
    return NSLocalizedString(minuteStringId, comment: "")
}

struct CafeView_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
//        CafeItemView(
//            cafeItem: CafeItem(id: "UUID", address: "Kimry chapaevo 22a", workingHours: "9:00 - 22:00", isOpenMessage: "Open", isOpenColor: Color.green, phone: "8999999999", latitude: 0, longitude: 0
//            )
//        )
    }
}
