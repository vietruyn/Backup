package com.example.admin.prclosechat_android;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.admin.prclosechat_android.adapter.PhoneBookAdapter;
import com.example.admin.prclosechat_android.connect.ConnectWebservice;
import com.example.admin.prclosechat_android.helper.PhoneBookOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ListPhoneBookActivity extends Activity {

    private ListView lvPhoneBook;

    private String phoneNumber = null;
    private String email = null;

    private Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    private String _ID = ContactsContract.Contacts._ID;
    private String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    private Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    private String DATA = ContactsContract.CommonDataKinds.Email.DATA;

    private StringBuffer output = new StringBuffer();

    private ProgressDialog mProgressDialog;
    private Dialog dialog;
    private LinearLayout lnCallapp, lnCallphone, lnChat, lnSms;
    private String url;
    private int type;
    private PhoneBookAdapter adapter;
    private PhoneBookOpenHelper openHelper_ob;
    private SimpleCursorAdapter cursorAdapter;
    private Cursor cursor;
    private SQLiteDatabase database_ob;
    private ContentResolver contentResolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);
        lvPhoneBook = (ListView) findViewById(R.id.lvPhoneNumber);
        contentResolver = getContentResolver();
        adapter = new PhoneBookAdapter(ListPhoneBookActivity.this);
        if (adapter.queryName().getCount() == 0) {
            new getListContact().execute();
        } else {
            String[] from = {openHelper_ob.FNAME, openHelper_ob.PNUMBER};
            int[] to = {R.id.tvName, R.id.tvPhoneNumber};
            cursor = adapter.queryName();
            cursorAdapter = new SimpleCursorAdapter(ListPhoneBookActivity.this,
                    R.layout.list_item_phonebook, cursor, from, to);
            lvPhoneBook.setAdapter(cursorAdapter);
        }
    }

    class getListContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ListPhoneBookActivity.this);
            mProgressDialog.setMessage("Please Waiting...");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            contentResolver = getContentResolver();
            adapter = new PhoneBookAdapter(ListPhoneBookActivity.this);
            Cursor cursor1 = contentResolver.query(CONTENT_URI, null, null, null, null);
            if (cursor1.getCount() > 0) {

                while (cursor1.moveToNext()) {
                    String contact_id = cursor1.getString(cursor1.getColumnIndex(_ID));
                    String name = cursor1.getString(cursor1.getColumnIndex(DISPLAY_NAME));

                    int hasPhoneNumber = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(HAS_PHONE_NUMBER)));

                    if (hasPhoneNumber > 0) {

                        output.append("\n First Name:" + name);
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            output.append("\n Phone number:" + phoneNumber);

                        }

                        /**
                         * Connect Sever
                         */
                        String url = "http://closechat.com/closechatwebservice/index.php?route=webservice/users/phonebook&username=?&array_telephone=" + phoneNumber.replaceAll("\\D+", "");
                        Log.e("URL: ", url);
                        ConnectWebservice connectWebservice = new ConnectWebservice(url);
                        connectWebservice.fetchJSON();
                        while (connectWebservice.parsingComplete) ;

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(connectWebservice.getData());
                            JSONObject number = jsonObject.getJSONObject(phoneNumber.replaceAll("\\D+", ""));
                            type = Integer.parseInt(number.getString("check"));
                            if (type == 2 || type == 3) {
                                name = number.getString("full_name");
                                email = number.getString("username");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        phoneCursor.close();

                        Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);

                        while (emailCursor.moveToNext()) {

                            email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                            output.append("\nEmail:" + email);
                        }

                        emailCursor.close();
                        adapter.insertDetails(name, phoneNumber, email, type);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.hide();

            String[] from = {openHelper_ob.FNAME, openHelper_ob.PNUMBER};
            int[] to = {R.id.tvName, R.id.tvPhoneNumber};
            cursor = adapter.queryName();
            cursorAdapter = new SimpleCursorAdapter(ListPhoneBookActivity.this,
                    R.layout.list_item_phonebook, cursor, from, to);
            lvPhoneBook.setAdapter(cursorAdapter);


        }
    }

}