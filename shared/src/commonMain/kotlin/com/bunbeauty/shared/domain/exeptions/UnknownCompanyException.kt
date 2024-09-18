package com.bunbeauty.shared.domain.exeptions

class UnknownCompanyException(flavor: String) : Exception("Company with flavor [$flavor] not found")
