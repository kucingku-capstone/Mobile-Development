package com.example.kucingku.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val email: String? = null,
    val username: String? = null,
    val photoUrl: String? = null,
    val bio: String? = null,
    val breedsInterest: String? = null,
    val genderInterest: String? = null,
    val ageInterest: String? = null
) {

}
