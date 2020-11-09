package com.android.tvnetdisk.entity


import com.google.gson.annotations.SerializedName

data class ColumnEntity(
    @SerializedName("columnName")
    val columnName: String = "",
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: String = ""
)