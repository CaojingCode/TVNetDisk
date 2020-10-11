package com.android.tvnetdisk.help

import android.graphics.Color
import java.util.*

fun getRandomColor(): Int {
    val random = Random()
    val r: Int = random.nextInt(256)
    val g: Int = random.nextInt(256)
    val b: Int = random.nextInt(256)
    return Color.rgb(r, g, b)
}