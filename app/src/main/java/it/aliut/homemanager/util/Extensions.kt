package it.aliut.homemanager.util

import android.graphics.Color
import kotlin.random.Random

fun randomColor(rnd: Random = Random(System.currentTimeMillis()), alpha: Int = 255): Int {
    return Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255))
}