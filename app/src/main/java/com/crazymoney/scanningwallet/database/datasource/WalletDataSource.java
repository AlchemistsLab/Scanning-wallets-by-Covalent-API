package com.crazymoney.scanningwallet.database.datasource;

import android.content.Context;
import android.util.Log;

import com.crazymoney.scanningwallet.database.DatabaseHelper;
import com.crazymoney.scanningwallet.database.DatabaseManager;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.j256.ormlite.stmt.PreparedQuery;

import java.util.List;

public class WalletDataSource {
	public static final String TAG = WalletDataSource.class.getSimpleName();

	private static WalletDataSource INSTANCE = null;
	private final DatabaseHelper helper;

	public static WalletDataSource getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new WalletDataSource(context);
		}
		return INSTANCE;
	}

	public WalletDataSource(Context context) {
		this.helper = DatabaseManager.getInstance(context).getHelper();
	}

	public void createWallet(Wallet wallet) {
		try {
			this.helper.getWalletDao().create(wallet);
		} catch (Exception e) {
			Log.e(TAG, "createWallet", e);
		}
	}

	public void deleteWallet(Wallet wallet) {
		try {
			this.helper.getWalletDao().delete(wallet);
		} catch (Exception e) {
			Log.e(TAG, "deleteWallet", e);
		}
	}

	public List<Wallet> getWallets() {
		try {
			return this.helper.getWalletDao().queryForAll();
		} catch (Exception e) {
			Log.e(TAG, "getWallets", e);
			return null;
		}
	}

	public Wallet getWallet(long id) {
		try {
			PreparedQuery<Wallet> preparedQuery = this.helper.
					getWalletDao().
					queryBuilder().
					where().
					eq(Wallet.Fields.ID, id).
					prepare();
			return this.helper.getWalletDao().queryForFirst(preparedQuery);
		} catch (Exception e) {
			Log.e(TAG, "getWallets", e);
			return null;
		}
	}
}
