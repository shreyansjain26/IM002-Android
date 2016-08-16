package com.example.shreyans.totp;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    String result;
    //String updateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getTOTP();
        /*
        DateFormat timeObj = new SimpleDateFormat("yyMMddHHmmSS");
        String time = timeObj.format(Calendar.getInstance().getTime());
        long seconds = 61 - (Long.parseLong(time.length() > 2 ? time.substring(time.length() - 2) : time)%60);
        Log.d("time",time);
        Log.d("delay",Long.toString(seconds));*/
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getTOTP();
            }
        },0,1000);

    }


    public void getTOTP() {
        //Log.d("getTOTP","init");
        DateFormat timeData = new SimpleDateFormat("yyMMddHHmm");
        String time = timeData.format(Calendar.getInstance().getTime());
        //Log.d("time",time);

        int timeInt = Integer.parseInt(time);


        String test = Integer.toHexString(timeInt);

        //DateFormat secData = new SimpleDateFormat("yyMMddHHmmSS");
        //final String updTime = timeData.format(Calendar.getInstance().getTime());
        //updateTime = updTime.substring(updTime.length()-2);

        String temp = "";
        String mykey = "qwerty4567";

        int c = 5;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(mykey.getBytes(), "HmacSHA1");
            mac.init(secret);
            byte[] digest = mac.doFinal(test.getBytes());
            //String enc = new String(digest);

            for (byte b : digest) {
                if (c >= 0) {
                    temp = temp + String.format("%02x", b);
                    c--;
                } else break;

            }

            result = Long.toString(Long.parseLong(temp, 16));
            result = result.length() >2 ? result.substring(result.length()-5): result;
            //result = result % 100000;
            //Log.d("result", String.valueOf(result));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) findViewById(R.id.textView)).setText((result));
                    //((TextView) findViewById(R.id.timeView)).setText(updateTime);
                    //Log.d("updated","updated");
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
