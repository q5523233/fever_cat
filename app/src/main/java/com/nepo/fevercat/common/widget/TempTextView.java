package com.nepo.fevercat.common.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by sm on 2019/3/18.
 */

public class TempTextView extends android.support.v7.widget.AppCompatTextView {
    String integralNum="37";//整数部分
    String decimal=".10";
    String unit = "℃";
    Paint paint;
    public TempTextView(Context context) {
        this(context, null);
    }

    public TempTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs)
    {
        paint=new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        if (integralNum == null&&getText().toString()!=null) {
//            String[] splitNums = getText().toString().split(".");
//            integralNum = splitNums[0];
//            if (splitNums.length > 1)
//                decimal = splitNums[1];
//            else
//                decimal="";
//        }
        canvas.drawText(integralNum,0,10,paint);
        canvas.drawText(decimal,paint.measureText(integralNum),10,paint);
    }
}
