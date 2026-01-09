package com.bunbeauty.shared.domain.feature.order

import com.bunbeauty.core.Constants.RUBLE_CURRENCY

data class ExtendedComment(
    val comment: String,
    val change: Change,
    val additionalUtensils: AdditionalUtensils,
) {
    data class Change(
        val paymentByCash: Boolean,
        val withoutChangeChecked: Boolean,
        val withoutChange: String,
        val changeFrom: String,
        val change: String,
    )

    data class AdditionalUtensils(
        val isAdditionalUtensils: Boolean,
        val count: String,
        val name: String,
    )
}

class GetExtendedCommentUseCase {
    operator fun invoke(extendedComment: ExtendedComment): String =
        buildString {
            extendedComment.comment
                .takeIf { comment ->
                    comment.isNotBlank()
                }?.let { comment ->
                    append(comment)
                }
            if (extendedComment.change.paymentByCash) {
                append(" ")
                append("(")
                if (extendedComment.change.withoutChangeChecked) {
                    append(extendedComment.change.withoutChange)
                } else {
                    append("${extendedComment.change.changeFrom} ${extendedComment.change.change} $RUBLE_CURRENCY")
                }
                append(")")
            }

            if (extendedComment.additionalUtensils.isAdditionalUtensils) {
                append(" ")
                append("(")
                append("${extendedComment.additionalUtensils.name} ${extendedComment.additionalUtensils.count}")
                append(")")
            }
        }.trim()
}
