package com.crazymoney.scanningwallet;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ScanningWalletApplication extends MultiDexApplication {

	private static final String TAG = ScanningWalletApplication.class.getSimpleName();


	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}
