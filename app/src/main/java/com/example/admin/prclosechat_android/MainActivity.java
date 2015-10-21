package com.example.admin.prclosechat_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.prclosechat_android.fragment.LoginFragment;
import com.example.admin.prclosechat_android.fragment.RegisterFragment;

public class MainActivity extends FragmentActivity {

    private LinearLayout lnLogin, lnSignUp;
    private Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (savedInstanceState == null) {
            fr = new LoginFragment();
            lnLogin.setBackgroundResource(R.color.click_button);
            lnSignUp.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);
        } else return;
    }

    /**
     * Initialization
     **/
    private void init() {
        lnLogin = (LinearLayout) findViewById(R.id.lnLogin);
        lnSignUp = (LinearLayout) findViewById(R.id.lnSignUp);
    }

    /**
     * Event change fragment when click tabs
     * * @param view
     */
    public void selectFrag(View view) {


        if (view == findViewById(R.id.lnSignUp)) {
            fr = new RegisterFragment();
            lnSignUp.setBackgroundResource(R.color.click_button);
            lnLogin.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);

        } else {
            fr = new LoginFragment();
            lnLogin.setBackgroundResource(R.color.click_button);
            lnSignUp.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);
        }


    }

    /**
     * Call fragment
     *
     * @param fr fragment show
     */
    private void callFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frm, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * finish activity when Back press
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Thanks for using application!!", Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}
