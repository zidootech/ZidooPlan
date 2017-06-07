package com.zidoo.realtek.rtc.selecttime;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zidoo.realtek.rtc.R;
import com.zidoo.rtk.hdmi.wheel.DateNumberRicAdapter;
import com.zidoo.rtk.hdmi.wheel.OnWheelChangedListener;
import com.zidoo.rtk.hdmi.wheel.OnWheelClickedListener;
import com.zidoo.rtk.hdmi.wheel.WheelView;

public class TimeWheelDialog implements OnClickListener, OnWheelClickedListener, OnWheelChangedListener {
    private Window              window     = null;
    public Button               btn_ok     = null;
    public Button               btn_cancle = null;

    public static interface ZidooDialogListener {
        public void onConfirm(Dialog dialog, int year, int mouth, int day, int hour, int munith, int second);

        public void onCancel(Dialog dialog);
    }

    private static String[]     months_big           = { "1", "3", "5", "7", "8", "10", "12" };
    private static String[]     months_little        = { "4", "6", "9", "11" };
    private List<String>        list_big             = Arrays.asList(months_big);
    private List<String>        list_little          = Arrays.asList(months_little);
    private static int          START_YEAR           = 1990, END_YEAR = 2100;

    private WheelView           yearView             = null;
    private WheelView           mouthView            = null;
    private WheelView           dayView              = null;
    private WheelView           hourView             = null;
    private WheelView           menuteView           = null;
    private WheelView           secondView           = null;
    private Calendar            mCalendar;
    private TextView            dialog_hint_view_title;
    private Button              btn_time_ok;
    private Button              btn_time_ancle;
    private boolean             isSelectTime         = false;
    private ZidooDialogListener mZidooDialogListener = null;
    private Dialog              mDialog              = null;
    private Context             mContext             = null;

    public Dialog getZidooDialog(final Context mContext, int myHour, int myMin, int mySencond,String titile,
            final ZidooDialogListener zidooDialogListener) {
        boolean isOffTake = true;
        boolean isSelectTime = true;
        this.mZidooDialogListener = zidooDialogListener;
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.transparent_theme);
        mCalendar = Calendar.getInstance();

        final int year = mCalendar.get(Calendar.YEAR);
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        final int mouth = mCalendar.get(Calendar.MONTH);
        final int hour = myHour == -1 ? mCalendar.get(Calendar.HOUR_OF_DAY) : myHour;
        final int minute = myMin == -1 ? mCalendar.get(Calendar.MINUTE) : myMin;
        final int second = mySencond == -1 ? mCalendar.get(Calendar.SECOND) : mySencond;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dia_view = inflater.inflate(R.layout.layout_pop_time, null);

        dialog_hint_view_title = (TextView) dia_view.findViewById(R.id.dialog_hint_view_title);
        dialog_hint_view_title.setText(titile);
        btn_time_ok = (Button) dia_view.findViewById(R.id.btn_time_ok);
        btn_time_ancle = (Button) dia_view.findViewById(R.id.btn_time_ancle);
        // yea
        yearView = (WheelView) dia_view.findViewById(R.id.wheel_yea);

        // mouth
        mouthView = (WheelView) dia_view.findViewById(R.id.wheel_mouth);

        // day
        dayView = (WheelView) dia_view.findViewById(R.id.wheel_day);

        hourView = (WheelView) dia_view.findViewById(R.id.wheel_hour);
        menuteView = (WheelView) dia_view.findViewById(R.id.wheel_menute);
        secondView = (WheelView) dia_view.findViewById(R.id.wheel_second);

        /******************************************************* cyb ***********************************************************************/
        yearView.setCutLineWidthFlag(true);
        mouthView.setCutLineWidthFlag(true);
        dayView.setCutLineWidthFlag(true);
        hourView.setCutLineWidthFlag(true);
        menuteView.setCutLineWidthFlag(true);
        secondView.setCutLineWidthFlag(true);
        /******************************************************* cyb ***********************************************************************/
        menuteView.requestFocus();

        if (isOffTake) {
            if (isSelectTime) {

                /******************************************************* cyb ***********************************************************************/

                hourView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 23, 0,false));
                hourView.setCurrentItem(0);
                DateNumberRicAdapter hourView_viewAdapter = (DateNumberRicAdapter) hourView.getViewAdapter();
                hourView_viewAdapter.setCurrent(0);

                menuteView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 59, 30,false));
                menuteView.setCurrentItem(30);
                DateNumberRicAdapter menuteView_viewAdapter = (DateNumberRicAdapter) menuteView.getViewAdapter();
                menuteView_viewAdapter.setCurrent(30);

                secondView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 59, 0,false));
                secondView.setCurrentItem(0);
                DateNumberRicAdapter secondView_viewAdapter = (DateNumberRicAdapter) secondView.getViewAdapter();
                secondView_viewAdapter.setCurrent(0);
                
                yearView.setVisibility(View.GONE);
                mouthView.setVisibility(View.GONE);
                dayView.setVisibility(View.GONE);
                hourView.setVisibility(View.VISIBLE);
                menuteView.setVisibility(View.VISIBLE);
                secondView.setVisibility(View.VISIBLE);

                /******************************************************* cyb ***********************************************************************/

            } else {
//                yearView.setViewAdapter(new DateNumberRicAdapter(mContext, START_YEAR, END_YEAR, year,false));
//                yearView.setCurrentItem(year - START_YEAR);
//
//                mouthView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 12, mouth,false));
//                mouthView.setCurrentItem(mouth);
//
//                // dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1,
//                // 30, day));
//
//                if (list_big.contains(String.valueOf(mouth + 1))) {
//                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,false));
//                } else if (list_little.contains(String.valueOf(mouth + 1))) {
//                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,false));
//                } else {
//                    // 闰年
//                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
//                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day.false));
//                    else
//                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,false));
//                }
//                dayView.setCurrentItem(day - 1);
//
//                yearView.setVisibility(View.VISIBLE);
//                mouthView.setVisibility(View.VISIBLE);
//                dayView.setVisibility(View.VISIBLE);
//                secondView.setVisibility(View.GONE);
//                hourView.setVisibility(View.GONE);
//                menuteView.setVisibility(View.GONE);
            }

        } else {
//            yearView.setViewAdapter(new DateNumberRicAdapter(mContext, START_YEAR, END_YEAR, year));
//            yearView.setCurrentItem(year - START_YEAR);
//
//            mouthView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 12, mouth));
//            mouthView.setCurrentItem(mouth);
//
//            // dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 23,
//            // day));
//            if (list_big.contains(String.valueOf(mouth + 1))) {
//                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day));
//            } else if (list_little.contains(String.valueOf(mouth + 1))) {
//                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day));
//            } else {
//                // 闰年
//                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
//                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day));
//                else
//                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day));
//            }
//            dayView.setCurrentItem(day - 1);
//
//            hourView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 23, hour));
//            hourView.setCurrentItem(hour);
//
//            menuteView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 59, minute));
//            menuteView.setCurrentItem(minute);
//
//            yearView.setVisibility(View.VISIBLE);
//            mouthView.setVisibility(View.VISIBLE);
//            dayView.setVisibility(View.VISIBLE);
//            hourView.setVisibility(View.VISIBLE);
//            menuteView.setVisibility(View.VISIBLE);
//            secondView.setVisibility(View.GONE);

        }

        yearView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                mCalendar.set(Calendar.HOUR_OF_DAY, newValue);
                if (list_big.contains(String.valueOf(mouthView.getCurrentItem() + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,false));

                } else if (list_little.contains(String.valueOf(mouthView.getCurrentItem() + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,false));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,false));
                    else
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,false));
                }

            }
        });

        mouthView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                int month_num = newValue + 1;
                if (list_big.contains(String.valueOf(month_num))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,false));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,false));
                } else {
                    if (((yearView.getCurrentItem() + START_YEAR) % 4 == 0 && (yearView.getCurrentItem() + START_YEAR) % 100 != 0)
                            || (yearView.getCurrentItem() + START_YEAR) % 400 == 0)
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,false));
                    else
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,false));
                }

            }
        });

        dayView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // mCalendar.set(Calendar.DAY_OF_MONTH, newValue);
            }
        });

        hourView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.HOUR_OF_DAY, newValue);
            }
        });

        menuteView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.MINUTE, newValue);

            }
        });
        secondView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.SECOND, newValue);

            }
        });

        if (list_big.contains(String.valueOf(mouth + 1))) {
            dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,false));
        } else if (list_little.contains(String.valueOf(mouth + 1))) {
            dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,false));
        } else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,false));
            else
                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,false));
        }
        mDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {

                        // return true;

                    }
                }
                return false;
            }
        });

        // btn_time_ok.setOnClickListener(new View.OnClickListener() {
        // public void onClick(View arg0) {
        // if (zidooDialogListener != null) {
        // int year = yearView.getCurrentItem() + START_YEAR;
        // int mouth = (mouthView.getCurrentItem() + 1);
        // int day = (dayView.getCurrentItem() + 1);
        // int hour = hourView.getCurrentItem();
        // int minute = menuteView.getCurrentItem();
        // zidooDialogListener.onConfirm(dialog, year, mouth, day, hour,
        // minute);
        // }
        // }
        // });
        // btn_time_ancle.setOnClickListener(new View.OnClickListener() {
        // public void onClick(View arg0) {
        // if (zidooDialogListener != null) {
        // zidooDialogListener.onCancel(dialog);
        // }
        // }
        // });
        yearView.setOnClickListener(this);
        mouthView.setOnClickListener(this);
        dayView.setOnClickListener(this);
        hourView.setOnClickListener(this);
        menuteView.setOnClickListener(this);
        secondView.setOnClickListener(this);
        /******************************************************* cyb ***********************************************************************/
        yearView.addClickingListener(this);
        mouthView.addClickingListener(this);
        dayView.addClickingListener(this);
        hourView.addClickingListener(this);
        menuteView.addClickingListener(this);
        secondView.addClickingListener(this);
        /******************************************************* cyb ***********************************************************************/
        yearView.addChangingListener(this);
        mouthView.addChangingListener(this);
        dayView.addChangingListener(this);
        hourView.addChangingListener(this);
        menuteView.addChangingListener(this);
        secondView.addChangingListener(this);
        /******************************************************* cyb ***********************************************************************/
        mDialog.setContentView(dia_view);
        Window window = mDialog.getWindow();
		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return mDialog;
    }

    // public static Dialog getTimeDialog(final Context mContext, int year, int
    // day, int mouth, int hour, int minute) {
    // final Dialog dialog = new Dialog(mContext, R.style.transparent_style);
    // LayoutInflater inflater = LayoutInflater.from(mContext);
    // View dia_view = inflater.inflate(R.layout.layout_pop_time, null);
    //
    // return dialog;
    // }

    public Dialog getZidooDialog(final Context mContext, final boolean isOffTake, final boolean isSelectTime,String titile,
            final ZidooDialogListener zidooDialogListener) {
        this.mZidooDialogListener = zidooDialogListener; 
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.transparent_theme);
        mCalendar = Calendar.getInstance();

        final int year = mCalendar.get(Calendar.YEAR);
        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        final int mouth = mCalendar.get(Calendar.MONTH);
        final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        final int minute = mCalendar.get(Calendar.MINUTE);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dia_view = inflater.inflate(R.layout.layout_pop_time_year, null);

        dialog_hint_view_title = (TextView) dia_view.findViewById(R.id.dialog_hint_view_title);
        dialog_hint_view_title.setText(titile);
        btn_time_ok = (Button) dia_view.findViewById(R.id.btn_time_ok);
        btn_time_ancle = (Button) dia_view.findViewById(R.id.btn_time_ancle);
        // yea
        yearView = (WheelView) dia_view.findViewById(R.id.wheel_yea);

        // mouth
        mouthView = (WheelView) dia_view.findViewById(R.id.wheel_mouth);

        // day
        dayView = (WheelView) dia_view.findViewById(R.id.wheel_day);

        hourView = (WheelView) dia_view.findViewById(R.id.wheel_hour);
        menuteView = (WheelView) dia_view.findViewById(R.id.wheel_menute);

        secondView = (WheelView) dia_view.findViewById(R.id.wheel_second);
        /******************************************************* cyb ***********************************************************************/
        yearView.setCutLineWidthFlag(false);
        mouthView.setCutLineWidthFlag(false);
        dayView.setCutLineWidthFlag(false);
        hourView.setCutLineWidthFlag(false);
        menuteView.setCutLineWidthFlag(false);
        secondView.setCutLineWidthFlag(false);
        /******************************************************* cyb ***********************************************************************/
        hourView.requestFocus();

        if (isOffTake) {

            if (isSelectTime) {
                hourView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 23, hour,true));
                hourView.setCurrentItem(hour);

                menuteView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 59, minute,true));
                menuteView.setCurrentItem(minute);

                yearView.setVisibility(View.GONE);
                secondView.setVisibility(View.GONE);
                mouthView.setVisibility(View.GONE);
                dayView.setVisibility(View.GONE);
                hourView.setVisibility(View.VISIBLE);
                menuteView.setVisibility(View.VISIBLE);

            } else {
                yearView.setViewAdapter(new DateNumberRicAdapter(mContext, START_YEAR, END_YEAR, year,true));
                yearView.setCurrentItem(year - START_YEAR);

                mouthView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 12, mouth,true));
                mouthView.setCurrentItem(mouth);

                // dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1,
                // 30, day));
                if (list_big.contains(String.valueOf(mouth + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,true));
                } else if (list_little.contains(String.valueOf(mouth + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,true));
                } else {
                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,true));
                    else
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,true));
                }
                dayView.setCurrentItem(day - 1);

                yearView.setVisibility(View.VISIBLE);
                mouthView.setVisibility(View.VISIBLE);
                dayView.setVisibility(View.VISIBLE);
                secondView.setVisibility(View.GONE);
                hourView.setVisibility(View.GONE);
                menuteView.setVisibility(View.GONE);
            }

        } else {
            /******************************************************* cyb ***********************************************************************/
            yearView.setViewAdapter(new DateNumberRicAdapter(mContext, START_YEAR, END_YEAR, year - START_YEAR,true));
            yearView.setCurrentItem(year - START_YEAR);
            DateNumberRicAdapter yearView_viewAdapter = (DateNumberRicAdapter) yearView.getViewAdapter();
            yearView_viewAdapter.setCurrent(year - START_YEAR);

            mouthView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 12, mouth,true));
            mouthView.setCurrentItem(mouth);
            DateNumberRicAdapter mouthView_viewAdapter = (DateNumberRicAdapter) mouthView.getViewAdapter();
            mouthView_viewAdapter.setCurrent(mouth);

            // dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30,
            // day));
            if (list_big.contains(String.valueOf(mouth + 1))) {
                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, (day - 1),true));
            } else if (list_little.contains(String.valueOf(mouth + 1))) {
                dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, (day - 1),true));
            } else {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, (day - 1),true));
                else
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, (day - 1),true));
            }
            dayView.setCurrentItem(day - 1);
            DateNumberRicAdapter dayView_viewAdapter = (DateNumberRicAdapter) dayView.getViewAdapter();
            dayView_viewAdapter.setCurrent(day - 1);

            hourView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 23, hour,true));
            hourView.setCurrentItem(hour);
            DateNumberRicAdapter hourView_viewAdapter = (DateNumberRicAdapter) hourView.getViewAdapter();
            hourView_viewAdapter.setCurrent(hour);

            menuteView.setViewAdapter(new DateNumberRicAdapter(mContext, 0, 59, minute,true));
            menuteView.setCurrentItem(minute);
            DateNumberRicAdapter menuteView_viewAdapter = (DateNumberRicAdapter) menuteView.getViewAdapter();
            menuteView_viewAdapter.setCurrent(minute);

            yearView.setVisibility(View.VISIBLE);
            secondView.setVisibility(View.GONE);
            mouthView.setVisibility(View.VISIBLE);
            dayView.setVisibility(View.VISIBLE);
            hourView.setVisibility(View.VISIBLE);
            menuteView.setVisibility(View.VISIBLE);
            /******************************************************* cyb ***********************************************************************/
        }

        yearView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                mCalendar.set(Calendar.HOUR_OF_DAY, newValue);
                if (list_big.contains(String.valueOf(mouthView.getCurrentItem() + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,true));

                } else if (list_little.contains(String.valueOf(mouthView.getCurrentItem() + 1))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,true));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,true));
                    else
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,true));
                }

            }
        });

        mouthView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                int month_num = newValue + 1;
                if (list_big.contains(String.valueOf(month_num))) {
                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 31, day,true));

                } else if (list_little.contains(String.valueOf(month_num))) {

                    dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 30, day,true));
                } else {
                    if (((yearView.getCurrentItem() + START_YEAR) % 4 == 0 && (yearView.getCurrentItem() + START_YEAR) % 100 != 0)
                            || (yearView.getCurrentItem() + START_YEAR) % 400 == 0)
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 29, day,true));
                    else
                        dayView.setViewAdapter(new DateNumberRicAdapter(mContext, 1, 28, day,true));
                }

            }
        });

        dayView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.DAY_OF_MONTH - 1, newValue);

            }
        });

        hourView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.HOUR_OF_DAY, newValue);
            }
        });

        menuteView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.MINUTE, newValue);

            }
        });
        secondView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCalendar.set(Calendar.SECOND, newValue);

            }
        });

        mDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
                        // return true;
                    }
                }
                return false;
            }
        });
        yearView.setOnClickListener(this);
        mouthView.setOnClickListener(this);
        dayView.setOnClickListener(this);
        hourView.setOnClickListener(this);
        menuteView.setOnClickListener(this);
        secondView.setOnClickListener(this);

        /******************************************************* cyb ***********************************************************************/
        // 鼠标事件
        yearView.addClickingListener(this);
        mouthView.addClickingListener(this);
        dayView.addClickingListener(this);
        hourView.addClickingListener(this);
        menuteView.addClickingListener(this);
        secondView.addClickingListener(this);
        /******************************************************* cyb ***********************************************************************/
        // 滑动事件
        yearView.addChangingListener(this);
        mouthView.addChangingListener(this);
        dayView.addChangingListener(this);
        hourView.addChangingListener(this);
        menuteView.addChangingListener(this);
        secondView.addChangingListener(this);

        /******************************************************* cyb ***********************************************************************/
        mDialog.setContentView(dia_view);
        Window window = mDialog.getWindow();
 		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        //
        // WindowManager.LayoutParams lp = window.getAttributes();
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        // lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        return mDialog;
    }

    public String getTime() {
        if (yearView == null || mouthView == null || dayView == null || hourView == null || menuteView == null
                || secondView == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append((yearView.getCurrentItem() + START_YEAR)).append("-").append((mouthView.getCurrentItem() + 1))
                .append("-").append((dayView.getCurrentItem() + 1)).append(" ").append(hourView.getCurrentItem())
                .append(":").append(menuteView.getCurrentItem()).append(":").append(secondView.getCurrentItem());
        return sb.toString();
    }

    @Override
    public void onClick(View v) {

        if (mZidooDialogListener != null) {
            int year = yearView.getCurrentItem() + START_YEAR;
            int mouth = (mouthView.getCurrentItem() + 1);
            int day = (dayView.getCurrentItem() + 1);
            int hour = hourView.getCurrentItem();
            int minute = menuteView.getCurrentItem();
            int second = secondView.getCurrentItem();
            mZidooDialogListener.onConfirm(mDialog, year, mouth, day, hour, minute, second);
        }
    }

    /******************************************************* cyb ***********************************************************************/
    @Override
    public void onItemClicked(WheelView wheel, int itemIndex) {
        if (mZidooDialogListener != null) {
            int year = yearView.getCurrentItem() + START_YEAR;
            int mouth = (mouthView.getCurrentItem() + 1);
            int day = (dayView.getCurrentItem() + 1);
            int hour = hourView.getCurrentItem();
            int minute = menuteView.getCurrentItem();
            int second = secondView.getCurrentItem();

            mZidooDialogListener.onConfirm(mDialog, year, mouth, day, hour, minute, second);
        }
    }

    /******************************************************* cyb ***********************************************************************/

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        DateNumberRicAdapter viewAdapter = (DateNumberRicAdapter) wheel.getViewAdapter();
        viewAdapter.setCurrent(newValue);
    }
    /******************************************************* cyb ***********************************************************************/

}
