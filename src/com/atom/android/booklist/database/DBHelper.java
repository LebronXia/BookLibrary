package com.atom.android.booklist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

	public static String name = "Booklibrary.db";
	public static int dbVersion = 1;
	
	
	String usersql = "create table if not exists userinfo" +
					"(userInfoId integer primary key autoincrement,"+
					"userNumber integer,"+
					"userName varchar, userPassword varchar,"+ 
					"telephone varchar)";
	
	String booksql = "create table if not exists book"+
					"(bookId integer primary key autoincrement,"+
					"bookNob integer,"+
					"bookName varchar,"+
					"bookWriter varchar,"+
					"bookPublish varchar,"+
					"bookNumber varchar,"+
					"bookImageurl varchar)";
	String  bookstatesql = "create table if not exists borrowrecord"+
					"(userId integer," +
					"bookId integer," +
					"bookFlag integer," +
					"borrowDate varchar," +
					"returnDate varchar," +
					"returnCount integer," +
					"ifCollection smallint)";
	public DBHelper(Context context) {
		super(context, name, null, dbVersion);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(usersql);
		db.execSQL(booksql);
		db.execSQL(bookstatesql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists book");
		db.execSQL("drop table if exists userinfo");
		db.execSQL("drop table if exists borrowrecord");
		onCreate(db);
	}

}
