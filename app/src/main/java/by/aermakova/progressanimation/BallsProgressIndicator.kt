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
    private var distanceBetween = 100f
    private var paneHeight = 250f
    private var paneWidth = ballRadius * 6 + distanceBetween * 2

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
                    ballRadius =
                        getFloat(R.styleable.BallsProgressIndicator_ball_radius, 20f)
                    distanceBetween =
                        getFloat(R.styleable.BallsProgressIndicator_distance_between, 100f)
                    paneHeight =
                        getFloat(R.styleable.BallsProgressIndicator_pane_height, 250f)
                }
            } finally {
                typedArray.recycle()
            }
        }
        initBalls()
    }

    private fun initBalls() {
        balls = arrayOf(
            BallImage(ballRadius, ballRadius, (paneHeight - ballRadius)).apply {
                color = firstBallColor
                delay = 300L
            },
            BallImage(ballRadius, (paneWidth / 2), (paneHeight - ballRadius)).apply {
                color = secondBallColor
                delay = 0L
            },
            BallImage(ballRadius, (paneWidth - ballRadius), (paneHeight - ballRadius)).apply {
                color = thirdBallColor
                delay = 150L
            }
        )
    }

    override fun onDraw(canvas: Canvas) {
        drawBalls(canvas, balls)
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
        visibility = VISIBLE
        balls.forEach { ball ->
            ball.startAnimation(this, (paneHeight - ballRadius), ballRadius)
        }
    }

    override fun onDetachedFromWindow() {
        balls.forEach { ball ->
            ball.stopAnimation()
        }
        visibility = GONE
        super.onDetachedFromWindow()
    }
}