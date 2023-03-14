package com.yoriworks.comiclibrary.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val GrayTransparentBackground = Color(0x55DDE4FF)
val GrayBackground = Color(0xFFD5E4FF)

val BlackBackground = Color(0xFF0e0e0e)
val DarkGrey = Color(0xff232326)
val GreyInactiveIcons = Color(0xff424345)
val LightGrey = Color(0xffbebec3)
val PurpleActions = Color(0xffbd32fe)
val PurpleDisable = Color(0xffbd32fe).copy(alpha = 0.4f)
val RedErrors = Color(color = 0xffff4a4a)

fun gradientCustom(primaryColor:Color = Color.White): Brush {
    return Brush.verticalGradient(0f to Color.Transparent,100f to primaryColor)
}

