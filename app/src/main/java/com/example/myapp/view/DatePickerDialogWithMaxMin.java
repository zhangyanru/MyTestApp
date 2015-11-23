package com.example.myapp.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by zyr
 * DATE: 15-11-19
 * Time: 下午9:47
 * Email: yanru.zhang@renren-inc.com
 */
public class DatePickerDialogWithMaxMin extends DatePickerDialog{
    private int maxYear = 2015;
    private int maxMonth;
    private int maxDay;

    public DatePickerDialogWithMaxMin(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        if(year>maxYear){
            view.init(maxYear,month,day,this);
        }
    }
}
