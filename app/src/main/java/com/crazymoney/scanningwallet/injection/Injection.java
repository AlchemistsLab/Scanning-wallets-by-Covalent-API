package com.crazymoney.scanningwallet.injection;

import android.content.Context;

import androidx.annotation.NonNull;

import com.crazymoney.scanningwallet.database.Repository;
import com.crazymoney.scanningwallet.database.datasource.WalletDataSource;
import com.crazymoney.scanningwallet.database.volley.CovalentVolley;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
	public static Context provideContext(@NonNull Context context) {
		return checkNotNull(context, "context cannot be null!");
	}

	public static Repository provideRepository(@NonNull Context context) {
		WalletDataSource walletDataSource = WalletDataSource.getInstance(context);
		CovalentVolley covalentVolley = CovalentVolley.getInstance(context);
		Repository repository = Repository.getInstance(walletDataSource, covalentVolley);
		return repository;
	}
}
