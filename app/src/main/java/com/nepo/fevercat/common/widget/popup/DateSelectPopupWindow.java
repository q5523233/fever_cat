package com.nepo.fevercat.common.widget.popup;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.nepo.fevercat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.widget.popup
 * 文件名:  DateSelectPopupWindow
 * 作者 :   <sen>
 * 时间 :  上午11:06 2017/6/28.
 * 描述 :  日期选择弹窗
 */

public class DateSelectPopupWindow extends BasePopupWindow {


    @BindView(R.id.date_picker_popup)
    WheelDatePicker datePickerPopup;

    @BindView(R.id.tv_date_popup_cancel)
    TextView tvDatePopupCancel;
    @BindView(R.id.tv_date_popup_confirm)
    TextView tvDatePopupConfirm;


    Context mContext;
    private View mView;

    private Calendar c = Calendar.getInstance();

    SimpleDateFormat SDF =
            new SimpleDateFormat("yyyy-M-d");

    private OnSelectItemListen mOnSelectItemListen;

    public DateSelectPopupWindow setOnSelectItemListen(OnSelectItemListen onSelectItemListen) {
        mOnSelectItemListen = onSelectItemListen;
        return this;
    }

    public DateSelectPopupWindow(Context context) {
        super(context);
        mContext = context;
        setTouchable(true);
        mView = LayoutInflater.from(context).inflate(R.layout.view_date_select_popup, null);
        setContentView(mView);
        ButterKnife.bind(this, mView);
        initView();

    }

    private void initView() {
        datePickerPopup.setVisibleItemCount(5);
        datePickerPopup.setCyclic(false);
        datePickerPopup.setItemTextSize(66);
        datePickerPopup.setSelectedItemTextColor(ContextCompat.getColor(mContext,R.color.color_02));


        tvDatePopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvDatePopupConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentDate = datePickerPopup.getCurrentDate();
                String format = SDF.format(currentDate);
                mOnSelectItemListen.selectItem(format);
                dismiss();
            }
        });



    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        datePickerPopup.initDateEnd();
        datePickerPopup.setSelectedYear(datePickerPopup.getYear());
        datePickerPopup.setSelectedMonth(datePickerPopup.getCurrentMonth());
        datePickerPopup.setSelectedDay(datePickerPopup.getCurrentDay());

    }

    public interface OnSelectItemListen {
        void selectItem(String content);
    }


}
