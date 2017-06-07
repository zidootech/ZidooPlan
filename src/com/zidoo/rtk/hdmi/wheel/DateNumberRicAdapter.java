package com.zidoo.rtk.hdmi.wheel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zidoo.realtek.rtc.R;

public class DateNumberRicAdapter extends NumericWheelAdapter {
    // Index of current item
    int currentItem;
    // Index of item to be highlighted
    int currentValue;
    int currentValueCustom;
    Context mContext;
    boolean isDiscolor;

    /**
     * Constructor
     */
    public DateNumberRicAdapter(Context context, int minValue, int maxValue, int current,boolean isDiscolor) {
        super(context, minValue, maxValue);
        mContext = context;
        this.isDiscolor = isDiscolor;
        this.currentValue = current;
        setTextSize(20);
        setTextColor(Color.GRAY);

    }
    public void setCurrent(int current) {
        this.currentValueCustom = current;
        notifyDataChangedEvent();
    }

    @Override
    protected void configureTextView(final TextView view) {
        
        super.configureTextView(view);
        
        Log.e("cyb", "currentValueCustom :" + currentValueCustom +" currentValue : " +currentValue +" currentItem :" +currentItem);
        
        
        if (currentItem == currentValue) {
                view.setTextColor(Color.WHITE); 
                if (currentValue == currentValueCustom) {
                    view.setTextSize(25);
                    view.setHeight(76);//
                }
        } else if (currentItem == currentValueCustom) {
            view.setTextColor(mContext.getResources().getColor(R.color.default_focus));
            view.setTextSize(25);
            view.setHeight(76);//
        }
        view.setTypeface(Typeface.SANS_SERIF);
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
        currentItem = index;
        return super.getItem(index, cachedView, parent);
    }

}
