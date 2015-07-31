package com.atom.android.booklist.database;

import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.UserInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private Context mContext = null;
	private SQLiteDatabase mSqLiteDatabase;
	private DBHelper dh = null;
	
	public DBManager(Context context) {
		dh = new DBHelper(context);
		mSqLiteDatabase = dh.getWritableDatabase();
	}
	
	//添加用户信息
	public void addUserInfo(UserInfo user){
		mSqLiteDatabase.execSQL("insert into userinfo values(null,?,?,?,?)", new Object[]{user.getUserId(),user.getName(),user.getPassword(),null});
	}
	//查询用户信息
	public UserInfo queryUser(){
		UserInfo user = new UserInfo();
		Cursor c = mSqLiteDatabase.rawQuery("select * from userinfo", null);
		while(c.moveToNext()){
			user.setUserId(c.getInt(c.getColumnIndex("userNumber")));
			user.setName(c.getString(c.getColumnIndex("userName")));
		}
			return user;
	}
	//添加借阅记录
	public void addBookRecords(BookInfo book){
		
	}
	
}
