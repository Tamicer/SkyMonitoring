package com.tamic.statsdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tamic.statinterface.stats.core.TcStatInterface;

import java.util.HashMap;

/**
 * Created by Tamic on 2016-03-16.
 */
public class SecondActivity extends BaseActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_activity_second);
        button = (Button) findViewById(R.id.second_button);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("onclick", v.toString());
        TcStatInterface.onEvent("second Activity", hashMap);

    }
}
