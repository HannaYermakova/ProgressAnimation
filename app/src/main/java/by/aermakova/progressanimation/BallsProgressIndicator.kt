package by.aermakova.progressanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable


class BallsProgressIndicator :
    View {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private var firstBallColor = Color.RED
    private var secondBallColor = Color.YELLOW
    private var thirdBallColor = Color.BLUE

    private var ballRadius = 20f
    private var distance = 100f
    private var paneHeight = 250
    private var paneWidth = ballRadius * 6 + distance * 2

    private lateinit var firstBall: BallImage
    private lateinit var secondBall: BallImage
    private lateinit var thirdBall: BallImage
    private lateinit var balls: Array<BallImage>

    private fun init(context: Context) {
        initBalls()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.BallsProgressIndicator, 0, 0)
            try {
                with(typedArray) {
                    firstBallColor =
                        getInt(R.styleable.BallsProgressIndicator_first_ball_color, Color.RED)
                    secondBallColor =
                        getInt(R.styleable.BallsProgressIndicator_second_ball_color, Color.YELLOW)
                    thirdBallColor =
                        getInt(R.styleable.BallsProgressIndicator_third_ball_color, Color.BLUE)
                }
            } finally {
                typedArray.recycle()
            }
        }
        initBalls()
    }

    private fun initBalls() {
        firstBall =
            BallImage(ballRadius, ballRadius, (paneHeight - ballRadius), firstBallColor, 300L)
        secondBall =
            BallImage(ballRadius, (paneWidth / 2), (paneHeight - ballRadius), secondBallColor, 0L)
        thirdBall = BallImage(
            ballRadius,
            (paneWidth - ballRadius),
            (paneHeight - ballRadius),
            thirdBallColor,
            150L
        )
        balls = arrayOf(firstBall, secondBall, thirdBall)
    }

    override fun onDraw(canvas: Canvas) {
        drawBalls(canvas, balls)
        super.onDraw(canvas)
    }

    private fun drawBalls(canvas: Canvas, balls: Array<BallImage>) {
        balls.forEach { ball ->
            ball.draw(canvas)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW = (paddingLeft + paddingRight + paneWidth).toInt()
        val w: Int = resolveSizeAndState(minW, widthMeasureSpec, 0)
        val minH = (paddingTop + paddingBottom + paneHeight).toInt()
        val h = resolveSizeAndState(minH, heightMeasureSpec, 0)
        setMeasuredDimension(w, h)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        balls.forEach { ball ->
            ball.setVisible(true, false)
            ball.startAnimation(this, (paneHeight - ballRadius), ballRadius)
        }
    }

    override fun onDetachedFromWindow() {
        balls.forEach { ball ->
            ball.stopAnimation()
            ball.setVisible(false, false)
        }
        super.onDetachedFromWindow()
    }
}