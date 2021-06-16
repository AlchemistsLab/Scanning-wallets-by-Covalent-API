package com.crazymoney.scanningwallet.database;

import android.content.Context;

public class DatabaseManager {
	private DatabaseHelper mHelper;
	private static DatabaseManager mInstance;

	private static final String TAG = DatabaseManager.class.getSimpleName();

	public static DatabaseManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DatabaseManager(context);
		}
		return mInstance;
	}

	private DatabaseManager(Context context) {
		mHelper = new DatabaseHelper(context);
	}

	public DatabaseHelper getHelper() {
		return mHelper;
	}
}
