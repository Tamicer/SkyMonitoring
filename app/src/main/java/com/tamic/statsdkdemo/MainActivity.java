package com.tamic.statsdkdemo;

import android.content.Intent;
import android.os.*;
import android.view.View;

import com.tamic.statInterface.statsdk.core.TcStatInterface;


public class MainActivity extends BaseActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                findViewById(R.id.id_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                TcStatInterface.onEvent("main", "onlick", "send data");
                                //发送数据
                                TcStatInterface.reportData();

                        }

                });

                findViewById(R.id.id_button2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                // test
                                TcStatInterface.onEventParameter("onclick", "open next");

                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                startActivity(intent);

                        }

                });
        }


}

