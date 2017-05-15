package com.example.deekin.myapplication;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by deekin on 15/05/17.
 */

public abstract class ClickListener implements View.OnClickListener {

    LinearLayout view;
    int i;

    public ClickListener(){}

    public ClickListener(LinearLayout view, int i){
        this.view = view;
        this.i = i;
    }

    @Override
    public abstract void onClick(View view);

    public LinearLayout getView() {
        return view;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
