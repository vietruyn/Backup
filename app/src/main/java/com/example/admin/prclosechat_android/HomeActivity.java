package com.example.admin.prclosechat_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Admin on 1/10/2015.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    private LinearLayout lnAddMoney, lnSetting, lnCallLog, lnCall, lnMessenger, lnInbox;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

    }

    /**
     * Initialization and call event when click icons from Home Activity
     */
    private void init() {
        lnAddMoney = (LinearLayout) findViewById(R.id.lnAddmoney);
        lnCall = (LinearLayout) findViewById(R.id.lnCall);
        lnCallLog = (LinearLayout) findViewById(R.id.lnCallLog);
        lnSetting = (LinearLayout) findViewById(R.id.lnSetting);
        lnInbox = (LinearLayout) findViewById(R.id.lnInbox);
        lnMessenger = (LinearLayout) findViewById(R.id.lnMessenger);

        lnAddMoney.setOnClickListener(this);
        lnCall.setOnClickListener(this);
        lnCallLog.setOnClickListener(this);
        lnSetting.setOnClickListener(this);
        lnInbox.setOnClickListener(this);
        lnMessenger.setOnClickListener(this);
    }

    /**
     * Enforcement event when clicking the icons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnAddmoney:
                showActivity();
                break;
            case R.id.lnCall:
                showActivity();
                break;
            case R.id.lnCallLog:
                showActivity();
                break;
            case R.id.lnSetting:
                showActivity();
                break;
            case R.id.lnInbox:
                showActivity();
                break;
            case R.id.lnMessenger:
                showActivity();

                break;

        }

    }

    /**
     * Show activity when click icons
     */


    private void showActivity() {
        mIntent = new Intent(HomeActivity.this, ListPhoneBookActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onBackPressed() {

        finish();
        return;
    }

}
