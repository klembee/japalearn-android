package com.japalearn.mobile.ui.misc

import android.content.Context
import android.graphics.Canvas
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import com.japalearn.mobile.R
import com.japalearn.mobile.utils.UnicodeUtils
import kotlin.math.ceil
import kotlin.math.max


/**
 * A text view that is able to display furigana on top of kanjis
 * @author Clement Bisaillon
 */
class FuriganaTextView(context: Context, attrs: AttributeSet? = null): TextView(context, attrs) {

    private val FURIGANA_PADDING_BOTTOM = 5

    private var textNotFormatted = ""
    private var formatedText = ""
    private lateinit var normalPaint: TextPaint
    private lateinit var furiganaPaint: TextPaint
    private var furiganaTextSize: Float = 10f
    private var lineSpacing: Float = furiganaTextSize / 2
    private var lineHeight = 0f
    private var sideMargins = 0f
    private val lines = ArrayList<Line>()

    init {
        this.initialize(context, attrs)
    }

    /**
     * Initializes the component
     */
    private fun initialize(context: Context, attrs: AttributeSet?) {
        val normalTextSize = paint.textSize

        normalPaint = TextPaint(paint)
        furiganaPaint = TextPaint(paint)
        furiganaTextSize = normalTextSize / 2

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.FuriganaTextView)
            textNotFormatted = typedArray.getString(R.styleable.FuriganaTextView_android_text) ?: ""
            textAlignment = typedArray.getInt(
                R.styleable.FuriganaTextView_android_textAlignment,
                View.TEXT_ALIGNMENT_TEXT_START
            )
//            maxLines = typedArray.getInt(R.styleable.FuriganaTextView_android_maxLines, -1)

            furiganaTextSize =
                typedArray.getDimension(R.styleable.FuriganaTextView_furiganaSize, furiganaTextSize)
            lineSpacing = typedArray.getFloat(
                R.styleable.FuriganaTextView_android_lineSpacingExtra,
                furiganaTextSize / 2
            )

            val marginLeftRight = typedArray.getDimension(
                R.styleable.FuriganaTextView_android_layout_marginLeft,
                0f
            ) + typedArray.getDimension(R.styleable.FuriganaTextView_android_layout_marginRight, 0f)
            var marginStartEnd = typedArray.getDimension(
                R.styleable.FuriganaTextView_android_layout_marginStart,
                0f
            ) + typedArray.getDimension(R.styleable.FuriganaTextView_android_layout_marginEnd, 0f)
            this.sideMargins = ceil(max(marginLeftRight, marginStartEnd))

            normalPaint.color = context.getColor(android.R.color.tab_indicator_text)
            furiganaPaint.color = context.getColor(android.R.color.tab_indicator_text)

            typedArray.recycle()
        }

        furiganaPaint.textSize = furiganaTextSize
        this.lineHeight = normalTextSize + furiganaTextSize// + lineSpacing
    }

    fun setText(text: TextWithFurigana){
        this.textNotFormatted = text.getUnformattedText()
        lines.clear()
        requestLayout()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if(lines.isNotEmpty()){
            var currentY = lineHeight
            lines.forEach {

                it.onDraw(canvas, currentY)
                currentY += lineHeight
            }
        }else{
            super.onDraw(canvas)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0

        this.cutInLines(width)

        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                width = widthSize
            }
            MeasureSpec.AT_MOST -> {
                width = getWidthOfLongestLine()
            }
            else -> width = widthSize
        }

        val maxHeight = ceil(lines.size.toFloat() * lineHeight).toInt() + 10

        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                height = heightSize
            }
            MeasureSpec.AT_MOST -> {
                if (maxHeight < heightSize) {
                    height = maxHeight
                } else {
                    height = heightSize
                }
            }
            else -> height = maxHeight
        }

        setMeasuredDimension(width, height)
    }

    private fun getWidthOfLongestLine(): Int{
        var longuest = 0

        lines.forEach {
            val size = normalPaint.measureText(it.text)
            if(size > longuest){
                longuest = size.toInt()
            }
        }

        return longuest
    }


    private fun cutInLines(width: Int) {
        this.lines.clear()

        val nonFormatedLines = ArrayList<String>()
        nonFormatedLines.addAll(this.textNotFormatted.split("\n"))

        nonFormatedLines.forEach {
            Log.i("ASDF", it)
            var formattedString = it
            val furiganas = HashMap<Int, TextWithFurigana>()

            var index = formattedString.indexOf('{')
            while(index != -1){

                val endFuriganaIndex = formattedString.indexOf("}")

                //Save the furigana before removing it
                val furigana = formattedString.substring(index + 1, endFuriganaIndex)
                val startingIndex = formattedString.lastIndexOf('[', index - 1)
                val word = formattedString.substring(startingIndex + 1, index)

                furiganas.put(startingIndex, TextWithFurigana(word, furigana))

                //Remove the furigana
                if(endFuriganaIndex != -1){
                    formattedString = formattedString.substring(0, index).replace("[", "") + formattedString.substring(endFuriganaIndex + 1).replaceFirst("]", "")
                }


                index = formattedString.indexOf('{')
            }

            val line = Line(formattedString)
            line.addFuriganas(furiganas)
            this.lines.add(line)

        }
        Log.i("ASDF", "======")

        text = text.replace("[\\n\\r]".toRegex(), "")
    }

    /**
     * Represents a line in the text
     */
    inner class Line(var text: String) {
        val furiganas = HashMap<Int, TextWithFurigana>()

        fun addFuriganas(furiganas: HashMap<Int, TextWithFurigana>) {
            this.furiganas.putAll(furiganas)
        }

        /**
         * Draws the line of text
         */
        fun onDraw(canvas: Canvas?, y: Float) {
            var xNormal = 0f
            var xFurigana = 0f
            if(textAlignment == View.TEXT_ALIGNMENT_CENTER){
                Log.i("ASDf", "CENTER")
                xNormal = (width / 2) - (normalPaint.measureText(text)) / 2.0f

            }

            canvas?.drawText(text, xNormal, y, normalPaint)

            //Draw the furigana
            furiganas.keys.forEach {
                val furigana = furiganas[it]
                val xLocation = normalPaint.measureText(text.substring(0, it))
                furigana?.let {textWithFurigana ->
                    //Make the furigana size the same as the word
                    //Draw one character at the time
                    var deltaX = 0f
                    textWithFurigana.furigana?.let {
                        var separation = normalPaint.measureText(textWithFurigana.text) / it.length
                        it.forEach {
                            canvas?.drawText(it.toString(), xLocation + deltaX, y - normalPaint.textSize, furiganaPaint)
                            deltaX += separation
                        }
                    }

                }
            }
        }
    }

}