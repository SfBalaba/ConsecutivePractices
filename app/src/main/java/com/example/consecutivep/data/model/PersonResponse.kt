package com.example.consecutivep.data.model

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PersonResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("enName") val enName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("profession") val profession: String?,
    @SerializedName("enProfession") val enProfession: String?)
