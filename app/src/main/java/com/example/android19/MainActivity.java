package com.example.android19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static int GOTmessage = 0;
    private final static int GOTit = 1;

    MyHandler hd = new MyHandler();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textview);
        Message msg = new Message();
        msg.what = GOTmessage;
        hd.sendMessage(msg);

        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        //pd.setTitle("");
        pd.setMessage("傳送message...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();


        new Thread(new Runnable(){
            @Override
            public void run() {

                int count = 0;
                while (true){
                    final int num = count;
                    pd.setProgress(num);
                    if(count>=100){
                        pd.dismiss();
                        Message msg = new Message();
                        msg.what = GOTit;
                        hd.sendMessage(msg);
                    }
                    count+=50;
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
        }).start();

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GOTmessage:
                    tv.setText("Got message");
                    break;
                case GOTit:
                    tv.setText("Got it");
                    break;
            }
        }
    }
}