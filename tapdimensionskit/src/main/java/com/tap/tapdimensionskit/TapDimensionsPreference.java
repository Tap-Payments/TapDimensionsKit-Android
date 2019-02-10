package com.tap.tapdimensionskit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TapDimensionsPreference extends Preference {

    private Button value;

    private int  counterMinValue;
    private int  counterMaxValue;
    private int  counterStepValue;

    private String counterIncomingTitle;


    private int currentValue;
    private TextView counterTitle;

    private SharedPreferences preferences;

    public TapDimensionsPreference(Context context) {
        super(context);
        init(context, null);
    }

    public TapDimensionsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TapDimensionsPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TapDimensionsPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleResource) {
        super(context,attrs,defStyleAttr,defStyleResource);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray a =  context.obtainStyledAttributes(attrs,R.styleable.TapDimensionsPreference);
        counterMinValue    =   a.getInt(R.styleable.TapDimensionsPreference_counterMinVal,0);
        counterMaxValue    =   a.getInt(R.styleable.TapDimensionsPreference_counterMaxVal,2000);
        counterStepValue   =   a.getInt(R.styleable.TapDimensionsPreference_counterStepVal,1);

        counterIncomingTitle  = a.getString(R.styleable.TapDimensionsPreference_title)!=null?
                a.getString(R.styleable.TapDimensionsPreference_title) :context.getString(R.string.default_title);


        a.recycle();
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultVal) {
        super.onSetInitialValue(restorePersistedValue, defaultVal);

        if (restorePersistedValue) currentValue = this.getPersistedInt(0) ;

        this.updateSummary();
    }


    @SuppressLint("ResourceType")
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        currentValue = a.getInt(index,0);
        return currentValue;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom, parent, false);

        RelativeLayout increment = view.findViewById(R.id.increment);
        RelativeLayout decrement = view.findViewById(R.id.decrement);

        value = view.findViewById(R.id.value);

        counterTitle = view.findViewById(R.id.counterTitle);


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((currentValue + counterStepValue) > counterMaxValue)
                    currentValue = counterMaxValue;
                else
                    currentValue = currentValue + counterStepValue;

                value.setText(currentValue+".0");

                SharedPreferences.Editor editor = getEditor();
                editor.putInt(getKey(), currentValue);
                editor.apply();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((currentValue - counterStepValue) < counterMinValue)
                    currentValue = counterMinValue;
                else
                    currentValue = currentValue - counterStepValue;

                value.setText(currentValue+".0");

                SharedPreferences.Editor editor = getEditor();
                editor.putInt(getKey(), currentValue);
                editor.apply();
            }
        });

        preferences = getSharedPreferences();
        int persistedValue = preferences.getInt(getKey(), currentValue);
        value.setText(String.valueOf(currentValue) + ".0");
        currentValue = persistedValue;
        counterTitle.setText(counterIncomingTitle);
        return view;
    }


    void updateSummary() {
        this.setSummary(String.valueOf(currentValue));
    }

}


