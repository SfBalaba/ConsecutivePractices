package com.example.consecutivep.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PosterResponse(
    val url: String?,
    @SerializedName("previewUrl") val previewUrl: String?
)
