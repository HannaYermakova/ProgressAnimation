package by.aermakova.progressanimation

import android.content.Context
import android.util.TypedValue

fun Context.pxToDp(px: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
