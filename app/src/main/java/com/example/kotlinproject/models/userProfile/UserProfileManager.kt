package com.example.kotlinproject.models.userProfile

import android.content.Context

class UserProfileManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    fun saveUserProfile(userProfile: UserProfile) {
        with(sharedPreferences.edit()) {
            putString("fullName", userProfile.fullName)
            putString("avatarUri", userProfile.avatarUri)
            putString("resumeUrl", userProfile.resumeUrl)
            putString("position", userProfile.position)
            apply()
        }
    }

    fun loadUserProfile(): UserProfile {
        return UserProfile(
            fullName = sharedPreferences.getString("fullName", "") ?: "",
            avatarUri = sharedPreferences.getString("avatarUri", null),
            resumeUrl = sharedPreferences.getString("resumeUrl", "") ?: "",
            position = sharedPreferences.getString("position", "") ?: ""
        )
    }
}