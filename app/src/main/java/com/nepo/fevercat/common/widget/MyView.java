package com.nepo.fevercat.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget
 * 文件名:  MyView
 * 作者 :   <sen>
 * 时间 :  下午4:55 2017/2/17.
 * 描述 :  练习自定义View 1
 */

public class MyView extends View {

    private Paint mPaint = new Paint();

    private int mWidth,mHeight;




    private void initPaint(){
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);
    }


    public MyView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
//                            getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec)
//        );

//        int widthSpecModel = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecModel = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthSpecModel== MeasureSpec.AT_MOST&&heightSpecModel== MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize,heightSpecSize);
//        }else if(widthSpecModel==MeasureSpec.AT_MOST){
//            setMeasuredDimension(widthSpecSize,widthMeasureSpec);
//        }else if(heightSpecModel==MeasureSpec.AT_MOST){
//            setMeasuredDimension(widthMeasureSpec,heightSpecSize);
//        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth=w;
        mHeight=h;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Bitmap bitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
//
//        Canvas canvas1 = new Canvas(bitmap);
//        canvas1.drawColor(Color.GREEN);
//
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);
//        paint.setTextSize(16);
//
//        canvas1.drawText("hello ls view",150,200,paint);
//

//        PorterDuff proter;
//        PorterDuffXfermode model;
//        RectF
//        canvas.drawRect();
//        canvas.drawLine();
//        BitmapShader





//        canvas.drawRect(100,100,400,800,paint);
//        paint.setColor(Color.BLACK);
//        canvas.drawRect(100,100,400,400,paint);
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(500,250,400,paint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
//        RectF rect = new RectF(400,400,800,800);
//        canvas.drawRect(rect,paint);

//        RectF rectF = new RectF(-500,-500,500,500);
//        // 绘制背景矩形
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectF,mPaint);
//
//        // 绘制圆弧
////        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rectF,180,90,true,mPaint);


        // 旋转画布
//        canvas.translate(mWidth/2,mHeight/2);
//        // 圆1
//        canvas.drawCircle(0,0,400,mPaint);
//        // 圆2
//        canvas.drawCircle(0,0,380,mPaint);
//        mPaint.setColor(Color.BLACK);

//        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
//            canvas.drawLine(0,80,0,400,mPaint);
//            canvas.rotate(10);
//            //canvas.scale(0.9f,0.9f,0,0);
//        }

        // 错切
//        canvas.translate(mWidth/2,mHeight/2);
//        RectF rectF = new RectF(0,0,200,200);
//        canvas.drawRect(rectF,mPaint);
//
//        canvas.skew(1,0);
//
//        mPaint.setColor(Color.BLACK);
//        canvas.drawRect(rectF,mPaint);

        // 刻度尺Demo
        drawTrans();



    }


    /**
     * translate 刻尺
     */
    private void drawTrans(){
    }





}
