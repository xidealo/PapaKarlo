package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.domain.model.street.Street

class GetFilteredStreetListUseCase {

    operator fun invoke(query: String, streetList: List<Street>) : List<Street> {
        return if (query.isEmpty()) {
            emptyList()
        } else {
            streetList.filter { street ->
                query.lowercase().split(" ").all { queryPart ->
                    street.name.lowercase().split(" ").any { namePart ->
                        namePart.startsWith(queryPart)
                    }
                } && (query != street.name)
            }.take(3)
        }
    }
}