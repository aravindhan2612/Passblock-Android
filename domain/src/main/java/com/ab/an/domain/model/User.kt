package com.ab.an.domain.model

data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val dob: String = "",
    val profilePicture: String = ""
)
