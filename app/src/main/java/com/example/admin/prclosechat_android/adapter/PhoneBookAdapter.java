package com.example.admin.prclosechat_android.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.prclosechat_android.R;
import com.example.admin.prclosechat_android.helper.PhoneBookOpenHelper;

public class PhoneBookAdapter extends BaseAdapter{
	SQLiteDatabase database_ob;
	PhoneBookOpenHelper openHelper_ob;
	Context context;

	public PhoneBookAdapter(Context c) {
		context = c;
	}

	public PhoneBookAdapter opnToRead() {
		openHelper_ob = new PhoneBookOpenHelper(context,
				openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
		database_ob = openHelper_ob.getReadableDatabase();
		return this;

	}

	public PhoneBookAdapter opnToWrite() {
		openHelper_ob = new PhoneBookOpenHelper(context,
				openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
		database_ob = openHelper_ob.getWritableDatabase();
		return this;

	}

	public void Close() {
		database_ob.close();
	}

	public long insertDetails(String fname, String phonenumber,String email, int type) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(openHelper_ob.FNAME, fname);
		contentValues.put(openHelper_ob.PNUMBER, phonenumber);
		contentValues.put(openHelper_ob.EMAIL, email);
		contentValues.put(openHelper_ob.TYPE, type);
		opnToWrite();
		long val = database_ob.insert(openHelper_ob.TABLE_NAME, null,
				contentValues);
		Close();
		return val;

	}

	public Cursor queryName() {
		String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.FNAME,
				openHelper_ob.PNUMBER, openHelper_ob.EMAIL, openHelper_ob.TYPE};
		opnToWrite();
		Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
				null, null, null, null);

		return c;
	}

	public Cursor queryAll(int nameId) {
		String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.FNAME,
				openHelper_ob.PNUMBER, openHelper_ob.EMAIL, openHelper_ob.TYPE};
		opnToWrite();
		Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols,
				openHelper_ob.KEY_ID + "=" + nameId, null, null, null, null, null);

		return c;

	}

	public long updateldetail(int rowId, String fname, String pnumber, String email, int type) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(openHelper_ob.FNAME, fname);
		contentValues.put(openHelper_ob.PNUMBER, pnumber);
		contentValues.put(openHelper_ob.EMAIL, email);
		contentValues.put(openHelper_ob.TYPE, type);
		opnToWrite();
		long val = database_ob.update(openHelper_ob.TABLE_NAME, contentValues,
				openHelper_ob.KEY_ID + "=" + rowId, null);
		Close();
		return val;
	}

	public int deletOneRecord(int rowId) {
		// TODO Auto-generated method stub
		opnToWrite();
		int val = database_ob.delete(openHelper_ob.TABLE_NAME,
				openHelper_ob.KEY_ID + "=" + rowId, null);
		Close();
		return val;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	class ViewHolder {
		private TextView tvUserName, tvPhoneNumber, tvEmail;
		private LinearLayout lnAppNotInstalled;
		private RelativeLayout lnAppInstalled;
		private ImageView imgAgree, imgCancer, imgAddFriend, imgSentRequest;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_phonebook, null);
			viewHolder = new ViewHolder();
			viewHolder.tvUserName = (TextView) view.findViewById(R.id.tvName);
			viewHolder.tvPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber);
			viewHolder.lnAppInstalled = (RelativeLayout) view.findViewById(R.id.lnAppInstalled);
			viewHolder.lnAppNotInstalled = (LinearLayout) view.findViewById(R.id.lnAppNotInstalled);
			viewHolder.imgAgree = (ImageView) view.findViewById(R.id.imgAgree);
			viewHolder.imgCancer = (ImageView) view.findViewById(R.id.imgCancer);
			viewHolder.imgAddFriend = (ImageView) view.findViewById(R.id.imgAddFriend);
			viewHolder.imgSentRequest = (ImageView) view.findViewById(R.id.imgSentRequest);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		Cursor c = queryAll(position);
		if (c.moveToFirst()) {
			do {
				viewHolder.tvUserName.setText(c.getString(1));
				viewHolder.tvPhoneNumber.setText(c.getString(2));

				int type= c.getInt(4);
				switch (type){
					case 2:
						view.setBackgroundResource(R.drawable.img_itemcheck);
						viewHolder.lnAppInstalled.setVisibility(View.INVISIBLE);
						viewHolder.lnAppNotInstalled.setVisibility(View.INVISIBLE);
						break;
					case 3:
						view.setBackgroundResource(R.drawable.img_itemcheck);
						viewHolder.lnAppInstalled.setVisibility(View.VISIBLE);
						viewHolder.lnAppNotInstalled.setVisibility(View.INVISIBLE);
						break;
					case 0:
						view.setBackgroundResource(R.drawable.img_itemuncheck);
						viewHolder.lnAppNotInstalled.setVisibility(View.VISIBLE);
						viewHolder.lnAppInstalled.setVisibility(View.INVISIBLE);
						break;
					case 1:
						view.setBackgroundResource(R.drawable.img_itemuncheck);
						viewHolder.lnAppNotInstalled.setVisibility(View.VISIBLE);
						viewHolder.lnAppInstalled.setVisibility(View.INVISIBLE);
						break;
				}


			} while (c.moveToNext());
		}

		return view;
	}
}
