package com.example.kotlinproject.models.userProfile

data class UserProfile(
    var fullName: String = "",
    var avatarUri: String? = null,
    var resumeUrl: String = "",
    var position: String = ""
)


