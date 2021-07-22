package com.crazymoney.scanningwallet.database;

import android.content.Context;

public class DatabaseManager {
	private final DatabaseHelper mHelper;
	private static DatabaseManager mInstance;

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
