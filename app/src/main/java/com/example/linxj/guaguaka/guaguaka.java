package com.example.linxj.guaguaka;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linxj on 2015/11/16.
 */
public class guaguaka extends View {
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mOutterPaint ;
    private Paint mBackPaint ;
    private String str = "谢谢";
    private Bitmap mOutterBitmap;
    private Path mPath;
    private int mLastX;
    private int mLastY;
    private boolean mComplete = false;
    /**
     * 记录刮奖信息文本的宽和高
     */
    private Rect mTextBound;

    private float mTextSize ;
    public guaguaka(Context context) {
        this(context,null);
        //init();
    }

    public guaguaka(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //init();
    }

    public guaguaka(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mOutterPaint = new Paint();
        mBackPaint = new Paint();
        mPath = new Path();
        // bitmap = BitmapFactory.decodeResource(getResources(),
        // com.imooc.guaguaka.R.drawable.t2);
        mOutterBitmap = BitmapFactory.decodeResource(getResources(),
               R.drawable.fg_guaguaka);
        str = "谢谢惠顾";
        mTextBound = new Rect();

        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                22, getResources().getDisplayMetrics());
    }
    /**
     * 设置绘制path画笔的一些属性
     */
    private void setupOutPaint()
    {
        mOutterPaint.setColor(Color.parseColor("#c0c0c0"));
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutterPaint.setStyle(Paint.Style.FILL);
        mOutterPaint.setStrokeWidth(20);
    }

    /**
     * 设置我们绘制获奖信息的画笔属性
     */
    private void setUpBackPaint()
    {

        mBackPaint.setColor(Color.RED);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setTextSize(mTextSize);
        // 获得当前画笔绘制文本的宽和高
        mBackPaint.getTextBounds(str, 0, str.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        // 初始化我们的bitmap
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        // 设置绘制path画笔的一些属性
        setupOutPaint();
        setUpBackPaint();

        // mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30,
                mOutterPaint);
        //mCanvas.drawBitmap(mOutterBitmap, null, new Rect(0, 0, width, height),
              //  null);
        //mCanvas.drawRect();
        mCanvas.drawColor(0xfcfcfc);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // 创建画笔


        canvas.drawText(str,getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2,mBackPaint);// 画文本
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null); //把mCanvas画出来

    }

    public void setText(String mText)
    {
        this.str = mText;
        // 获得当前画笔绘制文本的宽和高
        mBackPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    private void drawPath() {
        mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath,mOutterPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx > 3 || dy > 3)
                {
                    mPath.lineTo(x, y);
                }

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                if (!mComplete)
                    //new Thread(mRunnable).start();
                break;
        }
        if (!mComplete)
            invalidate();
        return true;

    }

}
