//
//  CafeItemView.swift
//  PapaKarloSwift
//
//  Created by Марк Шавловский on 09.03.2022.
//

import shared
import SwiftUI

struct CafeItemView: View {
    var cafeItem: CafeItem

    var body: some View {
        VStack(spacing: 0) {
            Text(cafeItem.address)
                .bodyMedium()
                .foregroundColor(AppColor.onSurface)
                .padding(.horizontal, Diems.MEDIUM_PADDING)
                .padding(.top, 12)
                .frame(maxWidth: .infinity, alignment: .leading)

            HStack(spacing: 0) {
                Text(cafeItem.workingHours)
                    .labelMedium(weight: .medium)
                    .foregroundColor(AppColor.onSurfaceVariant)

                Text(getCafeStatusText(cafeStatus: cafeItem.cafeOpenState))
                    .labelMedium(weight: .medium)
                    .foregroundColor(getCafeStatusColor(cafeStatus: cafeItem.cafeOpenState))
                    .padding(.leading, 4)
            }
            .padding(.horizontal, Diems.MEDIUM_PADDING)
            .padding(.top, 8)
            .frame(maxWidth: .infinity, alignment: .leading)

            FoodDeliveryDivider()
                .padding(.top, 12)
                .padding(.horizontal, 16)
        }.background(AppColor.surface)
    }
}

func getCafeStatusColor(cafeStatus: CafeOpenState) -> Color {
    switch cafeStatus {
    case is CafeOpenState.Opened:
        return AppColor.positive
    case is CafeOpenState.CloseSoon:
        return AppColor.warning
    case is CafeOpenState.Closed:
        return AppColor.negative
    default:
        return AppColor.warning
    }
}

func getCafeStatusText(cafeStatus: CafeOpenState) -> String {
    switch cafeStatus {
    case is CafeOpenState.Opened:
        return NSLocalizedString("msg_cafe_open", comment: "")
    case is CafeOpenState.CloseSoon:
        let minutes = Int((cafeStatus as? CafeOpenState.CloseSoon)?.minutesUntil ?? 0)
        let minuteString = getMinuteString(
            closeIn: minutes
        )
        return NSLocalizedString("msg_cafe_close_soon", comment: "") + String(minutes) + " " + minuteString
    case is CafeOpenState.Closed:
        return NSLocalizedString("msg_cafe_closed", comment: "")
    default:
        return "CLose"
    }
}

private func getMinuteString(closeIn: Int) -> String {
    let minuteStringId: String
    switch closeIn {
    case 10 ... 19:
        minuteStringId = "msg_cafe_minutes"
    case let x where x % 10 == 1:
        minuteStringId = "msg_cafe_minute"
    case 2 ... 4:
        minuteStringId = "msg_cafe_minutes_234"
    default:
        minuteStringId = "msg_cafe_minutes"
    }
    return NSLocalizedString(minuteStringId, comment: "")
}
