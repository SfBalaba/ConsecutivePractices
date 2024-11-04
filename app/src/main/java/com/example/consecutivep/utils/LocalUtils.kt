package com.example.consecutivep.utils

import androidx.compose.runtime.mutableStateOf

object LocalUtils {
    var isFilter = mutableStateOf(false)

    val types = listOf("movie",  "anime", "cartoon")

    val contentStatus = listOf("announced", "completed", "filming",
        "post-production", "pre-production")

}