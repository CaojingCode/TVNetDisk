package com.android.tvnetdisk.entity


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CampusResourcesEntity(
    @SerializedName("fileName")
    val fileName: String = "",
    @SerializedName("fileURL")
    val fileURL: String = "",
    @SerializedName("folderName")
    val folderName: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("resourcesType")
    val resourcesType: Int = 0,
    @SerializedName("sort")
    val sort: Int = 0,
    @SerializedName("thumbnailURL")
    val thumbnailURL: String = "",
    @SerializedName("type")
    val type: Int = 0
)