package com.crazymoney.scanningwallet.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crazymoney.scanningwallet.database.table.Wallet;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.Method;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	private Dao<Wallet, Integer> walletDao = null;

	public DatabaseHelper(Context context) {
		super(context, "ScanningWalletByCQT", null, 1);
	}

	@Override
	public void close() {
		super.close();
		this.walletDao = null;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "create database");
			TableUtils.createTable(connectionSource, Wallet.class);
		} catch (SQLException e) {
			Log.e(TAG, "onCreate", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
						  int newVersion) {
		try {
			Log.i(TAG, "upgrade: " + oldVersion + "--" + newVersion);
			if (oldVersion < newVersion) {
				for (int i = oldVersion; i < newVersion; i++) {
					String methodName = "updateFromDatabaseVersion" + i;
					Method method = getClass().getDeclaredMethod(methodName, (Class<?>[]) null);
					method.setAccessible(true);
					method.invoke(this, (Class<?>[]) null);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onUpgrade", e);
		}
	}

	public Dao<Wallet, Integer> getWalletDao() {
		if (null == this.walletDao) {
			try {
				this.walletDao = super.getDao(Wallet.class);
			} catch (java.sql.SQLException e) {
				Log.e(TAG, "getWalletDao", e);
			}
		}
		return this.walletDao;
	}
}
