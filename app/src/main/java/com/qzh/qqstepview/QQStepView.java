package com.qzh.qqstepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;//20px
    private int mStepTextSize;
    private int mStepTextColor;

    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mStepMax;//总步数
    private int mCurrentStep;//当前步数

    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 1、分析效果

        // 2、确定自定义属性，编写attrs.xml

        // 3、在布局中使用

        // 4、在自定义view中获取自定义属性

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.QQStepView);

        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);

        array.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);//画笔空心
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);//画笔空心
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);

    }


    // 5、omMeasure
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    // 6、画外圆孤，内圆孤，文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 6.1 画外圆孤
        RectF rectF = new RectF(mBorderWidth,mBorderWidth,getWidth()-mBorderWidth,getHeight()-mBorderWidth);
        canvas.drawArc(rectF,135,270,false,mOuterPaint);

        if(mStepMax==0) return;
        // 6.2 画内圆孤
        float sweepAngle = (float) mCurrentStep/mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);

        // 6.3 画文字
        String mStepText = mCurrentStep+"";
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(mStepText,0,mStepText.length(),textBound);
        int dx = getWidth()/2 - textBound.width()/2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(mStepText,dx,baseLine,mTextPaint);
    }

    // 设置最大步数
    public synchronized void setSetpMax(int stepMax){
        this.mStepMax = stepMax;
    }

    // 设置最大步数
    public synchronized void setCurrenStep(int currenStep){
        this.mCurrentStep = currenStep;
        //不断绘制
        invalidate();
    }


}
