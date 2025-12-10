package com.bunbeauty.core

class UnknownCompanyException(flavor: String) : Exception("Company with flavor [$flavor] not found")