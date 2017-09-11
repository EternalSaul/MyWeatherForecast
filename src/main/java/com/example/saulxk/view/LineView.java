package com.example.saulxk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by Dandy on 2016/11/21.
 */

public class LineView extends View {
    /**
     * view的总高度
     */
    private  int mViewHeight;

    /**
     * view的总宽度
     */
    private int mViewWidth;

    /**
     * 温度字体大小
     */
    private int mTempTextSize=25;

    /**
     * 温度字体颜色
     */
    private int mTempTextColor=Color.BLACK;

    /**
     * 线的宽度
     */
    private int mWeaLineWidth = 5;

    /**
     * 圆点的半径
     */
    private int mWeaDotRadius = 10;

    /**
     * 画圆圈的画笔与画线的笔
     */
    private Paint mDotPaint;

    private Paint mLinePaint;
    /**
     * 画灰色线的笔与画温度的笔
     */
    private Paint mGrayLinePaint;

    private TextPaint mTempPaint;

    /**
     * 文字和点的间距
     */
    private int mTextDotDistance = 20;

    /**
     * 坐标点文字偏移量
     */
    private static final int POINT_TEXT_OFFSET = 10;
    /**
     *  最高温集合中温度差
     */
    private float mHighsTempest;

    /**
     *  最低温集合中温度差
     */
    private int mLowsTempest;

    /**
     * 最高温数组
     */
    private List<Integer> mHighs;

    /**
     * 最低温数组
     */
    private List<Integer> mLows;

    /**
     * 与顶部和底部的间距
     */
    private final int mMarginTopAndrBottom=15;

    /**
     *每个点的间隔X轴
     */
    private int mInterval;

    /**
     * 第一个点的X坐标
     */
    private float mFristX;

    /**
     * 折线图高度（单个）
     */
    private float mLineHeight;
    /**
     * 灰色线的间隔
     */
    private int mSpace=200;
    //高温线的颜色
    private final  static int LINE_COLOR_HIGH=Color.RED;
    //低温线的颜色
    private final static int LINE_COLOR_LOW=Color.BLUE;

    public LineView(Context context) {
        this(context,null);
}

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth= MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight=MeasureSpec.getSize(heightMeasureSpec);
        computeSize();
    }

    /**
     * 计算相关大小
     */
    private void computeSize(){
        mInterval=mViewWidth/mHighs.size();
        mFristX=mInterval/2;
        mLineHeight=(mViewHeight/5)*2;
        mLineHeight=mLineHeight-80;
        mSpace=mViewHeight/5;
//        mLineHeight=mLineHeight-(80);
//        mSpace=mViewHeight/5*3;
    }

    /**
     * 设置七天最高温与最低温集合
     * @param highs
     * @param lows
     */
    public void setHighsAndLows(List<Integer> highs, List<Integer> lows){
        this.mHighs=highs;
        this.mLows=lows;
    }

    private int mHighsLowest;
    /**
     * 设置七天中高温里面最高温与最低温
     * @param highest
     * @param lowest
     */
    public void setHighsest(int highest,int lowest){
        this.mHighsLowest=lowest;
        if (lowest<=0&&highest>=0){
            mHighsTempest=highest+Math.abs(lowest);
        }else if (highest<=0){
            mHighsTempest=Math.abs(highest)-Math.abs(lowest);
        }else if (highest>=0&&lowest>=0){
            mHighsTempest=highest-lowest;
        }
    }

    private  int mLowsLowsest;
    /**
     * 设置七天中低温里面最高温与最低温
     * @param highest
     * @param lowest
     */
    public void setLowsest(int highest,int lowest){
        this.mLowsLowsest=lowest;
        if (lowest<=0&&highest>=0){
            mLowsTempest=highest+Math.abs(lowest);
        }else if (highest<=0){
            mLowsTempest=Math.abs(highest)-Math.abs(lowest);
        }else if (highest>=0&&lowest>=0){
            mLowsTempest=highest-lowest;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        drawGrayLine(canvas);
        drawLine(canvas);
        drawDot(canvas);
        drawTemp(canvas);
    }

    /**
     * 初始化各个画笔
     */
    private void initPaint() {
        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setStyle(Paint.Style.FILL);
//        mDotPaint.setColor(Color.WHITE);

        mTempPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTempPaint.setTextSize(mTempTextSize);
        mTempPaint.setColor(mTempTextColor);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mWeaLineWidth);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(LINE_COLOR_HIGH);

        mGrayLinePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mGrayLinePaint.setStyle(Paint.Style.STROKE);
        mGrayLinePaint.setStrokeWidth(2.0f);
        //mGrayLinePaint.setColor(Color.GRAY);
        mGrayLinePaint.setColor(Color.argb(100,195,192,192));
    }

    private void drawGrayLine(Canvas canvas) {
        int space=mViewHeight/5;
        for (int i = 0; i <= 5; i++) {
            canvas.drawLine(0,space*i,mViewWidth,space*i,mGrayLinePaint);
        }
    }

    private void drawLine(Canvas canvas) {
        float highsBaseY=mLineHeight/mHighsTempest;//最高温中每隔一度对应相隔多少y坐标
        float lowsBaseY=mLineHeight/mLowsTempest;//最低温中每隔一度对应相隔多少y坐标
        float y1,y2=0f;//y1起点y坐标，y2 终点y坐标
        float x1,x2=0f;
        //绘制高温
        for (int i=0;i<mHighs.size()-1;i++){
            x1=mFristX+mInterval*i;
            x2=mFristX+mInterval*(i+1);

            y1=mHighs.get(i)-mHighsLowest;
            y1=highsBaseY*y1-(mMarginTopAndrBottom*3);
            y1=mLineHeight-y1;

            y2=mHighs.get(i+1)-mHighsLowest;
            y2=highsBaseY*y2-(mMarginTopAndrBottom*3);
            y2=mLineHeight-y2;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            canvas.drawLine(x1,y1,x2,y2,mLinePaint);

        }
        //绘制低温
        mLinePaint.setColor(LINE_COLOR_LOW);
        for (int i=0;i<mLows.size()-1;i++){
            x1=mFristX+mInterval*i;
            x2=mFristX+mInterval*(i+1);

            y1=mLows.get(i)-mLowsLowsest;
            y1=lowsBaseY*y1-(mMarginTopAndrBottom*3);
            y1=mLineHeight-y1+mSpace;

            y2=mLows.get(i+1)-mLowsLowsest;
            y2=lowsBaseY*y2-(mMarginTopAndrBottom*3);
            y2=mLineHeight-y2+mSpace;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            canvas.drawLine(x1,y1,x2,y2,mLinePaint);

        }
    }


    private void drawDot(Canvas canvas) {
        float highsBaseY=mLineHeight/mHighsTempest;//最高温中每隔一度对应相隔多少y坐标
        float lowsBaseY=mLineHeight/mLowsTempest;//最低温中每隔一度对应相隔多少y坐标
        float y=0f;
        float x=0f;
        for (int i = 0; i < mHighs.size(); i++) {

            y=mHighs.get(i)-mHighsLowest;
            x=mFristX+mInterval*i;
            y=highsBaseY*y-(mMarginTopAndrBottom*3);
            y=mLineHeight-y;
            mDotPaint.setColor(Color.RED);
            canvas.drawCircle(x,y,mWeaDotRadius,mDotPaint);

        }

        for (int i = 0; i < mLows.size(); i++) {
            y=mLows.get(i)-mLowsLowsest;
            y=lowsBaseY*y-(mMarginTopAndrBottom*3);
            y=mLineHeight-y+mSpace;
            mDotPaint.setColor(Color.BLUE);
            canvas.drawCircle(mFristX+mInterval*i,y,mWeaDotRadius,mDotPaint);

        }


    }

    /**
     * 绘制温度（文本）
     * @param canvas
     */
    private void drawTemp(Canvas canvas){
        float baseY=mLineHeight/mHighsTempest;//每隔一度对应相隔多少y坐标
        float lowsBaseY=mLineHeight/mLowsTempest;//最低温中每隔一度对应相隔多少y坐标
        float y=0f;
        float x=0f;
        for (int i = 0; i < mHighs.size(); i++) {
            y=mHighs.get(i)-mHighsLowest;
            y=baseY*y-(mMarginTopAndrBottom*3);
            y=mLineHeight-y-mTextDotDistance;
            x=mFristX+mInterval*i-POINT_TEXT_OFFSET;
            canvas.drawText(String.valueOf(mHighs.get(i))+"°",x,y,mTempPaint);
        }

        for (int i = 0; i < mHighs.size(); i++) {
            y=mLows.get(i)-mLowsLowsest;
            y=lowsBaseY*y-(mMarginTopAndrBottom*3);
            y=mLineHeight-y+mSpace+mTextDotDistance+10;
            x=mFristX+mInterval*i-POINT_TEXT_OFFSET;
            canvas.drawText(String.valueOf(mLows.get(i))+"°",x,y,mTempPaint);
        }

    }


}
