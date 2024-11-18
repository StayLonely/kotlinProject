package com.example.kotlinproject.utils

import androidx.compose.runtime.mutableStateOf
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Util {
    const val Base = "https://api.mangadex.org"

    val tagsMap = mapOf(
        "Comedy" to "4d32cc48-9f00-4cca-9b5a-a839f0764984",
        "Reincarnation" to "0bc90acb-ccc1-44ca-a34a-b9f3a73259d0",
        "School Life" to "caaa44eb-cd40-4177-b930-79d3ef2afe87",
        "Ninja" to "489dd859-9b61-4c37-af75-5b18e88daafc",
        "Superhero" to "7064a261-a137-4d3a-8848-2d385de3a99c",
        "Historical" to "33771934-028e-4cb3-8744-691e866a923e",
        "Romance" to "423e2eae-a7a2-4a8b-ac03-a8351462d71d"
    )
    val statuses = listOf("ongoing", "completed", "hiatus")
    val contentRatings = listOf("safe", "suggestive", "erotica", "pornographic")

    var isFilter = mutableStateOf(false)


    fun getTagNamesByIds(ids: List<String>): List<String> {
        return ids.mapNotNull { id ->
            tagsMap.entries.find { it.value == id }?.key
        }
    }
    fun getTagIdsByNames(tagNames: List<String>?): List<String>? {
        return tagNames?.mapNotNull { name ->
            tagsMap[name]
        }
    }


    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    private val REQUEST_CODE_PERMISSIONS = 101

    fun allPermissionsGranted(activity: Activity): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                activity.applicationContext, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(activity: Activity) {
        if (!allPermissionsGranted(activity)) {
            ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }





}