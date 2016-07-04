package com.tamic.statsdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tamic.statInterface.statsdk.core.TcStatInterface;

/**
 * Created by Tamic on 2016-03-16.
 */
public class SecondActivity extends BaseActivity implements View.OnClickListener {
    Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_activity_second);
        button =(Button)findViewById(R.id.second_button);
        button.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        TcStatInterface.onEvent("second Activity", "onclik", "testButton");
    }
}
