package com.tamic.statsdkdemo;

import android.content.Intent;
import android.os.*;
import android.view.View;

import com.tamic.statInterface.statsdk.core.TcStatInterface;

import java.util.HashMap;


public class MainActivity extends BaseActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                findViewById(R.id.id_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // 按钮埋了点
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("参数1", "xxx");
                                map.put("参数2", "yyyy");

                                TcStatInterface.onEvent("main", map);
                                //发送数据
                                TcStatInterface.reportData();

                        }

                });

                findViewById(R.id.id_button2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("参数3", "打开第二界面");
                                map.put("参数4", "打开第二界面22");
                                TcStatInterface.onEvent("open", map);


                                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                startActivity(intent);

                        }

                });
        }


}

