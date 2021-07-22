package com.crazymoney.scanningwallet.database.datasource;

import android.content.Context;
import android.util.Log;

import com.crazymoney.scanningwallet.database.DatabaseHelper;
import com.crazymoney.scanningwallet.database.DatabaseManager;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.j256.ormlite.stmt.PreparedQuery;

import java.util.List;

public class WalletDataSource extends BaseDataSource {
	public static final String TAG = WalletDataSource.class.getSimpleName();

	private static WalletDataSource INSTANCE = null;
	private final Context context;
	private final DatabaseHelper helper;


	public static WalletDataSource getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new WalletDataSource(context);
		}
		return INSTANCE;
	}

	public static void destroyInstance() {
		INSTANCE = null;
	}

	public WalletDataSource(Context context) {
		this.context = context;
		this.helper = DatabaseManager.getInstance(context).getHelper();
	}

	public boolean createWallet(Wallet wallet) {
		try {
			int affectedRows = this.helper.getWalletDao().create(wallet);
			return getBooleanFromAffectedRows(affectedRows);
		} catch (Exception e) {
			Log.e(TAG, "createWallet", e);
			return false;
		}
	}

	public boolean deleteWallet(Wallet wallet) {
		try {
			int affectedRows = this.helper.getWalletDao().delete(wallet);
			return getBooleanFromAffectedRows(affectedRows);
		} catch (Exception e) {
			Log.e(TAG, "deleteWallet", e);
			return false;
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
			Wallet wallet = this.helper.getWalletDao().queryForFirst(preparedQuery);
			return wallet;
		} catch (Exception e) {
			Log.e(TAG, "getWallets", e);
			return null;
		}
	}
}
