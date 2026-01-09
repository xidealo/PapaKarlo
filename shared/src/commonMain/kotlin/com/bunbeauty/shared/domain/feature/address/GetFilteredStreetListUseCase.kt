package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.core.model.street.Street

class GetFilteredStreetListUseCase {
    operator fun invoke(
        query: String,
        streetList: List<Street>,
    ): List<Street> =
        if (query.isEmpty()) {
            emptyList()
        } else {
            streetList
                .filter { street ->
                    query.lowercase().split(" ").all { queryPart ->
                        street.name.lowercase().split(" ").any { namePart ->
                            namePart.startsWith(queryPart)
                        }
                    } &&
                        (query != street.name)
                }.take(3)
        }
}
