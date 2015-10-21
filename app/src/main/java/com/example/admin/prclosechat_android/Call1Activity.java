package com.example.admin.prclosechat_android;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Admin on 1/10/2015.
 */
public class Call1Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call1);
    }
    @Override
    public void onBackPressed() {

        finish();
        return;
    }
}
