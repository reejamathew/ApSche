package com.mdev.apsche.model

data class Apartment(
    var aptId: Int?,
    var aptNo: Int?,
    var tenant_name: String,
    var phone_no: String,
    var lease_period: String,
    var lease_amount: String,
    var beds: Int
)
