package com.bunbeauty.designsystem

class UnknownCompanyException(flavor: String) : Exception("Company with flavor [$flavor] not found")