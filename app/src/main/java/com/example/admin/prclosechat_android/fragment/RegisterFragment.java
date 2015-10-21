package com.example.admin.prclosechat_android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.prclosechat_android.R;
import com.example.admin.prclosechat_android.connect.ConnectWebservice;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Admin on 29/9/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button btnJoinNow;
    private EditText edtFullname, edtPhonenumber, edtEmail, edtPassword, edtConfirmPassword;
    private String mEmail, mPassWord, mConfirmPassword, mFullname, mPhonenumber, emailPattern;
    private RelativeLayout rlHideKeyboard;
    private String android_id, android_name, check;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_register, container, false);
        init(view);
        eventHideKeyboad();


        return view;

    }

    /**
     * Hide key board
     */
    private void eventHideKeyboad() {
        rlHideKeyboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Khỏi tạo
     *
     * @param mView
     */
    private void init(View mView) {
        rlHideKeyboard = (RelativeLayout) mView.findViewById(R.id.rlHideKeyboard);
        edtFullname = (EditText) mView.findViewById(R.id.edtFullName);
        edtPassword = (EditText) mView.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) mView.findViewById(R.id.edtConfirmPassword);
        edtPhonenumber = (EditText) mView.findViewById(R.id.edtPhoneNumber);
        edtEmail = (EditText) mView.findViewById(R.id.edtEmail);
        btnJoinNow = (Button) mView.findViewById(R.id.btnJoinNow);

        btnJoinNow.setOnClickListener(this);
    }

    /**
     * Sự kiện khi click vào các item
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        getData();
        final ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        /**
         * Kiểm tra kết nối internet
         */
        if (activeNetwork != null && activeNetwork.isConnected()) {
            /**
             * Khi có kết nối
             */
            switch (v.getId()) {
                case R.id.btnJoinNow:
                    if (mEmail.isEmpty() || mPassWord.isEmpty() || mConfirmPassword.isEmpty() || mPhonenumber.isEmpty() || mFullname.isEmpty()) {
                        Toast.makeText(getActivity(), "Not be empty!", Toast.LENGTH_LONG).show();
                    } else if (mPassWord.compareTo(mConfirmPassword) != 0) {
                        edtConfirmPassword.setError("Passwords don't match");
                    } else if (!mEmail.matches(emailPattern)) {
                        edtEmail.setError("Invalid email address");
                    } else {
                        //Put the value
                        getData();
                        String url = "http://closechat.com/closechatwebservice/index.php?route=webservice/users/register&username=" + mEmail
                                + "&%20register_type=1&device_id=" + android_id + "&device_name=" + android_name + "&lebanese=0&password=" + mPassWord
                                + "&fullname=" + mFullname + "&telephone=" + mPhonenumber;
                        Log.e("URL: ", url);
                        String test = url.replaceAll(" ", "%20");
                        ConnectWebservice connectWebservice = new ConnectWebservice(test);
                        connectWebservice.fetchJSON();
                        while (connectWebservice.parsingComplete) ;

                        if (connectWebservice.getData() != null) {

                            try {
                                JSONObject jsonObject = new JSONObject(connectWebservice.getData());
                                check = jsonObject.getString("check");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        switch (check) {
                            case "0":
                                toastMessenger("Email or phone is null");
                                break;
                            case "1":
                                toastMessenger("Email is invalid");
                                break;
                            case "2":
                                toastMessenger("Phone is invalid");
                                break;
                            case "3":
                                toastMessenger("Account is exist, but not same device -> send sms/email deactive");
                                break;
                            case "4":
                                toastMessenger("Register type is wrong");
                                break;
                            case "5":
                                toastMessenger("Insert new account successful");
                                LoginFragment ldf = new LoginFragment();
                                getFragmentManager().beginTransaction().add(R.id.frm, ldf).commit();
                                break;
                            case "6":
                                toastMessenger("Account is exist, same device");
                                break;
                            case "7":
                                toastMessenger("Device id is null");
                                break;
                            case "8":
                                toastMessenger("Device name is null");
                                break;
                            case "9":
                                toastMessenger("Email not send successful");
                                break;
                        }
                    }
                    break;
                default:
            }
        } else {
            /**\
             * Khi mất kết nối
             */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Internet is not connected");

            alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    /**
     * Hiển thị thông báo
     *
     * @param a Chuổi tring muốn hiển thị
     */
    private void toastMessenger(String a) {
        Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gán giá trị vào biến
     */
    private void getData() {
        mEmail = edtEmail.getText().toString();
        mPassWord = edtPassword.getText().toString();
        mConfirmPassword = edtConfirmPassword.getText().toString();
        mFullname = edtFullname.getText().toString();
        mPhonenumber = edtPhonenumber.getText().toString();
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        android_name = android.os.Build.MODEL;

        emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    }

}
