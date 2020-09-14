package by.aermakova.progressanimation

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.view.View

class BallImage(
    private val radius: Float,
    private val centerX: Float,
    private var centerY: Float,
    var color: Int,
    private val delay: Long
) :
    Drawable() {

    private val moveDuration = 450L
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var animator: ValueAnimator

    init {
        paint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        paint.color = color
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    fun startAnimation(view: View, from: Float, to: Float) {
        animator = ValueAnimator.ofFloat(from, to).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = moveDuration
            startDelay = delay
            addUpdateListener { valueAnimator ->
                centerY = (valueAnimator.animatedValue as Float)
                view.invalidate()
            }
        }
        animator.start()
        view.invalidate()
    }

    fun stopAnimation() {
        animator.cancel()
    }

    override fun setAlpha(p0: Int) {
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {
    }
}