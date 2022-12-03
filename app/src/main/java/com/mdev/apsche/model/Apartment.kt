package com.mdev.apsche.model

data class Apartment(
    var aptId: Int?,
    var aptNo: Int?,
    var tenant_name: String,
    var phone_no: String,
    var lease_information: String,
    var lease_amount: Int?,

    var beds_bath: String
)
