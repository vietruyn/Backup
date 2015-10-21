package com.example.admin.prclosechat_android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created by Admin on 1/10/2015.
 */
public class Call2Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_call2);
    }
    @Override
    public void onBackPressed() {

        finish();
        return;
    }
}
